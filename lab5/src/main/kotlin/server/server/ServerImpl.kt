package server.server

import com.sun.jdi.connect.spi.ClosedConnectionException
import kotlinx.coroutines.*
import org.slf4j.LoggerFactory
import server.doorman.Doorman
import share.executor.stream_executor.StreamExecutor
import share.io.input.InputFactory
import share.io.output.OutputFactory
import share.security.encoder.EncoderFactory
import share.security.encoder_manager.ContextEncoderManager
import java.net.ServerSocket
import java.net.Socket

class ServerImpl(
    private val streamExecutor: StreamExecutor,
    private val inputFactory: InputFactory,
    private val outputFactory: OutputFactory,
    private val executorOutputFactory: OutputFactory,
    private val encoderFactory: EncoderFactory,
    private val doorman: Doorman,
    private val encoderManager: ContextEncoderManager
) : Server {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    private suspend fun accept(clientSocket: Socket) = coroutineScope {
        val address = clientSocket.inetAddress

        logger.info("Connection from {} accepted", address)

        val clientInput = async(Dispatchers.IO) {
            return@async inputFactory.buildInput(clientSocket.getInputStream())
        }.await()

        val clientOutput = async(Dispatchers.IO) {
            return@async outputFactory.buildOutput(clientSocket.getOutputStream())
        }.await()

        val encoder = encoderFactory.buildCryptoManager(clientInput, clientOutput)

        logger.info("Initiating security module...")
        encoder.init()
        logger.info("Security module has been successfully initialized")

        val contextElement = encoderManager.put(encoder)

        withContext(Dispatchers.Default + contextElement) {
            val executorClientOutput = executorOutputFactory.buildOutput(clientOutput)

            logger.info("Greeting {}", address)

            try {
                doorman.greet(clientInput, clientOutput, encoderManager.get())
            } catch (e: ClosedConnectionException){
                return@withContext
            }
            streamExecutor.execute(clientInput, executorClientOutput)
        }
    }

    override suspend fun start(port: Int) = coroutineScope {
        val initJob = async(Dispatchers.IO) {
            val serverSocket = ServerSocket(port)
            logger.info("Server started at port {}", port)

            return@async serverSocket
        }

        val serverSocket = initJob.await()

        while (true) {
            val clientSocket = async(Dispatchers.IO) {
                return@async serverSocket.accept()
            }.await()

            val address = clientSocket.inetAddress
            clientSocket.use {
                launch(Dispatchers.Default) {
                    accept(clientSocket)
                }.join()
            }
            logger.info("Connection {} closed", address)
        }
    }
}