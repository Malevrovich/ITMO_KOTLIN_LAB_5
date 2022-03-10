import commands.util.*
import data.IdGenerator
import data.coordinates.CoordinatesBuilder
import data.coordinates.CoordinatesBuilderImpl
import data.coordinates.CoordinatesReader
import data.coordinates.CoordinatesReaderImpl
import data.movie.MovieBuilder
import data.movie.MovieBuilderImpl
import data.movie.MovieReader
import data.movie.MovieReaderImpl
import data.person.PersonBuilder
import data.person.PersonBuilderImpl
import data.person.PersonReader
import data.person.PersonReaderImpl
import executor.*
import org.kodein.di.*
import storage.FileStorageKeeper
import storage.LinkedHashSetStorage
import storage.Storage
import storage.StorageKeeper
import java.io.IOException

fun main(args: Array<String>) {

    val di = DI.direct {
        bindSingleton<CommandFactory> { CommandFactoryImpl(instance(), instance(), instance(), instance()) }
        bindSingleton<CommandReader> { CommandReaderImpl(instance(), instance()) }

        bindSingleton<CoordinatesReader> { CoordinatesReaderImpl(instance()) }
        bindSingleton<CoordinatesBuilder> { CoordinatesBuilderImpl() }

        bindSingleton<MovieReader> { MovieReaderImpl(instance(), instance(), instance()) }
        bindSingleton<MovieBuilder> { MovieBuilderImpl(instance(), instance(), instance()) }

        bindSingleton<PersonReader> { PersonReaderImpl(instance()) }
        bindSingleton<PersonBuilder> { PersonBuilderImpl() }

        bindSingleton<Executor> { ExecutorImpl() }

        bindProvider<Storage> { LinkedHashSetStorage(instance()) }
        bindSingleton<StorageKeeper> { FileStorageKeeper(instance(), instance()) }
        bindSingleton { StreamExecutorKeeper() }

        bindSingleton<StreamExecutor> {
            val res = StreamExecutorImpl(instance(), instance())

            val streamExecutorKeeper: StreamExecutorKeeper = instance()
            streamExecutorKeeper.currentStreamExecutor = res

            res
        }

        bindSingleton { IdGenerator() }
    }

    if(args.isEmpty()){
        println("Программа должна принимать на вход имя файла " +
                "для инициализации коллекции в качестве первого аргумента")
        return
    }

    val storageKeeper: StorageKeeper = di.instance()
    storageKeeper.openAndParse(args[0])

    val streamExecutor: StreamExecutor = di.instance()

    try {
        streamExecutor.execute(System.`in`, System.out)
    } catch (e: IOException){
        println("Невозможно воспользоваться входным потоком: ${e.message}")
    }
}