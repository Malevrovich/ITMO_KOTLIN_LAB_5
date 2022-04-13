package server.server

import server.storage.StorageKeeper
import share.executor.stream_executor.StreamExecutor
import share.user_io.user_reader.UserReaderFactory
import share.user_io.user_writer.UserWriterFactory
import java.net.ServerSocket

class ServerImpl(
    val streamExecutor: StreamExecutor,
    val userReaderFactory: UserReaderFactory,
    val userWriterFactory: UserWriterFactory,
    val storageKeeper: StorageKeeper
) : Server {

    override fun start(port: Int) {
        val serverSocket = ServerSocket(port)
        while (true) {
            val clientSocket = serverSocket.accept()
            println("Accepted")

            val userReader = userReaderFactory.buildUserReader(clientSocket.getInputStream())
            val userWriter = userWriterFactory.buildUserWriter(clientSocket.getOutputStream())

            streamExecutor.execute(userReader, userWriter)

            storageKeeper.save()

            clientSocket.close()
        }
    }
}