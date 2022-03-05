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
import executor.Executor
import executor.ExecutorImpl
import executor.StreamExecutor
import executor.StreamExecutorImpl
import org.kodein.di.*
import storage.FileStorageKeeper
import storage.LinkedHashSetStorage
import storage.Storage
import storage.StorageKeeper
import util.ParseException
import java.io.FileInputStream
import java.io.IOException
import java.util.*

fun main(args: Array<String>) {
    val di = DI {
        bindProvider<CommandFactory> { CommandFactoryImpl(di) }
        bindProvider<CommandReader> { CommandReaderImpl(di) }

        bindProvider<CoordinatesReader> { CoordinatesReaderImpl(di) }
        bindProvider<CoordinatesBuilder> { CoordinatesBuilderImpl() }
        bindSingleton { CommandFactoryConfigure() }

        bindProvider<MovieReader> { MovieReaderImpl(di) }
        bindProvider<MovieBuilder> { MovieBuilderImpl(di) }

        bindProvider<PersonReader> { PersonReaderImpl(di) }
        bindProvider<PersonBuilder> { PersonBuilderImpl() }

        bindProvider<Storage> { LinkedHashSetStorage(di) }
        bindProvider<Executor> { ExecutorImpl() }
        bindProvider<StreamExecutor> { StreamExecutorImpl(di) }

        bindSingleton<StorageKeeper> { FileStorageKeeper(di) }

        bindSingleton { IdGenerator() }
    }

    if(args.isEmpty()){
        println("Программа должна принимать на вход имя файла " +
                "для инициализации коллекции в качестве первого аргумента")
        return
    }

    val storageKeeper: StorageKeeper by di.instance()
    storageKeeper.open(args[0])

    val streamExecutor: StreamExecutor by di.instance()

    try {
        streamExecutor.execute(System.`in`, System.out)
    } catch (e: IOException){
        println("Невозможно воспользоваться входным потоком: ${e.message}")
    }
}