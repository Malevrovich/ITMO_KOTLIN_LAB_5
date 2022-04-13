package server

import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import server.commands.CommandFromDTOMapper
import server.commands.CommandFromDTOMapperImpl
import server.commands.JSONReader
import server.data.IdGenerator
import server.storage.FileStorageKeeper
import server.storage.LinkedHashSetStorage
import server.storage.Storage
import server.storage.StorageKeeper
import share.executor.Executor
import server.executor.ExecutorImpl
import server.server.Server
import server.server.ServerImpl
import server.user_io.user_writer.ConnectionUserWriterFactory
import share.commands.util.CommandReader
import share.executor.stream_executor.StreamExecutor
import share.executor.stream_executor.StreamExecutorImpl
import share.executor.stream_executor.StreamExecutorKeeper
import share.user_io.user_reader.UserReaderFactory
import share.user_io.user_reader.UserReaderFactoryImpl
import share.user_io.user_writer.UserWriterFactory
import share.user_io.user_writer.PrintUserWriterFactory

fun main(args: Array<String>) {

    val di = DI.direct {
        bindSingleton<CommandReader> { JSONReader() }

        bindSingleton<Executor> { ExecutorImpl(instance()) }

        bindProvider<Storage> { LinkedHashSetStorage() }
        bindSingleton<StorageKeeper> { FileStorageKeeper(instance(), instance()) }
        bindSingleton { StreamExecutorKeeper() }

        bindSingleton<StreamExecutor> {
            val res = StreamExecutorImpl(instance(), instance())

            val streamExecutorKeeper: StreamExecutorKeeper = instance()
            streamExecutorKeeper.currentStreamExecutor = res

            res
        }

        bindSingleton { IdGenerator() }

        bindSingleton<CommandFromDTOMapper> { CommandFromDTOMapperImpl(instance(), instance(), instance()) }

        bindSingleton<UserWriterFactory> { ConnectionUserWriterFactory() }
        bindSingleton<UserReaderFactory> { UserReaderFactoryImpl() }

        bindSingleton<Server> { ServerImpl(instance(), instance(), instance(), instance()) }
    }

    if(args.isEmpty()){
        println("Программа должна принимать на вход имя файла " +
                "для инициализации коллекции в качестве первого аргумента")
        return
    }

    val storageKeeper: StorageKeeper = di.instance()
    storageKeeper.openAndParse(args[0])

    val server: Server = di.instance()
    server.start(6346)
}