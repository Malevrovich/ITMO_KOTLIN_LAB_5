package client

import client.commands.CommandDTOFactoryImpl
import client.commands.CommandReaderImpl
import client.data.coordinates.CoordinatesBuilder
import client.data.coordinates.CoordinatesBuilderImpl
import client.data.coordinates.CoordinatesReader
import client.data.coordinates.CoordinatesReaderImpl
import client.data.movie.MovieBuilder
import client.data.movie.MovieBuilderImpl
import client.data.movie.MovieReader
import client.data.movie.MovieReaderImpl
import client.data.person.PersonBuilder
import client.data.person.PersonBuilderImpl
import client.data.person.PersonReader
import client.data.person.PersonReaderImpl
import client.executor.ServerExecutor
import client.connection_io.connection.Connection
import client.connection_io.connection.SocketChannelConnection
import client.connection_io.connection_reader.ServerReader
import client.connection_io.connection_reader.ServerReaderImpl
import client.connection_io.connection_writer.ServerWriter
import client.connection_io.connection_writer.ServerWriterImpl
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import server.data.IdGenerator
import share.commands.util.CommandDTOFactory
import share.commands.util.CommandReader
import share.executor.Executor
import share.executor.stream_executor.StreamExecutor
import share.executor.stream_executor.StreamExecutorImpl
import share.executor.stream_executor.StreamExecutorKeeper
import share.user_io.user_reader.ScannerUserReader
import share.user_io.user_reader.UserReader
import share.user_io.user_reader.UserReaderFactory
import share.user_io.user_reader.UserReaderFactoryImpl
import share.user_io.user_writer.PrintUserWriter
import share.user_io.user_writer.UserWriter
import share.user_io.user_writer.UserWriterFactory
import share.user_io.user_writer.PrintUserWriterFactory
import java.io.IOException
import java.net.InetAddress
import java.net.InetSocketAddress

fun main() {
    val addr = InetSocketAddress(InetAddress.getLocalHost(), 6346)

    val di = DI.direct {
        bindSingleton { addr }

        bindSingleton<CommandDTOFactory> { CommandDTOFactoryImpl() }

        bindSingleton { IdGenerator() }

        bindSingleton<CoordinatesBuilder> { CoordinatesBuilderImpl() }
        bindSingleton<PersonBuilder> { PersonBuilderImpl() }
        bindSingleton<MovieBuilder> { MovieBuilderImpl(instance(), instance(), instance()) }

        bindSingleton<CoordinatesReader> { CoordinatesReaderImpl(instance()) }
        bindSingleton<PersonReader> { PersonReaderImpl(instance()) }
        bindSingleton<MovieReader> { MovieReaderImpl(instance(), instance(), instance()) }

        bindSingleton<Executor> { ServerExecutor(instance(), instance(), instance(), instance(), instance()) }

        bindSingleton { StreamExecutorKeeper() }

        bindSingleton<StreamExecutor> {
            val res = StreamExecutorImpl(instance(), instance())

            val streamExecutorKeeper: StreamExecutorKeeper = instance()
            streamExecutorKeeper.currentStreamExecutor = res

            res
        }

        bindSingleton<UserReaderFactory> { UserReaderFactoryImpl() }
        bindSingleton<UserWriterFactory> { PrintUserWriterFactory() }

        bindSingleton<UserReader> { ScannerUserReader(System.`in`) }
        bindSingleton<UserWriter> { PrintUserWriter(System.out) }

        bindSingleton<ServerReader> { ServerReaderImpl(instance()) }
        bindSingleton<ServerWriter> { ServerWriterImpl(instance()) }

        bindSingleton<Connection> { SocketChannelConnection(addr) }

        bindSingleton<CommandReader> { CommandReaderImpl(instance(), instance()) }
    }

    val streamExecutor: StreamExecutor = di.instance()
    val userReader: UserReader = di.instance()
    val userWriter: UserWriter = di.instance()

    try {
        streamExecutor.execute(userReader, userWriter)
    } catch (e: IOException){
        println("Невозможно воспользоваться входным потоком.")
        println(e.message)
    }

    di.instance<Connection>().close()
}