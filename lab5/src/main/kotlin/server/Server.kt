package server

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import org.kodein.di.*
import org.postgresql.ds.PGConnectionPoolDataSource
import server.commands.dto.CommandFromDTOMapperServer
import server.commands.util.JSONReader
import server.database.event.*
import server.database.repository.PostgresRepository
import server.database.repository.PostgresUserManager
import server.database.repository.Repository
import server.database.repository.UserManager
import server.doorman.Doorman
import server.doorman.DoormanImpl
import server.executor.CheckAccessExecutor
import server.server.Server
import server.server.ServerImpl
import server.storage.main.MainStorage
import server.storage.main.RepositorySetMainStorage
import server.storage.data.Data
import server.storage.data.LinkedHashSetData
import server.storage.secondary.SecondaryMapStorage
import server.storage.secondary.SecondaryStorage
import server.storage.storage_keeper.PostgreSQLStorageKeeper
import server.storage.storage_keeper.StorageKeeper
import share.commands.util.CommandReader
import share.data.coordinates.Coordinates
import share.data.coordinates.CoordinatesBuilder
import share.data.coordinates.CoordinatesBuilderImpl
import share.data.movie.Movie
import share.data.movie.MovieBuilder
import share.data.movie.MovieBuilderImpl
import share.data.person.Person
import share.data.person.PersonBuilder
import share.data.person.PersonBuilderImpl
import share.data.user.UserBuilder
import share.data.user.UserBuilderImpl
import share.executor.Executor
import share.executor.ExecutorImpl
import share.executor.stream_executor.StreamExecutor
import share.executor.stream_executor.StreamExecutorImpl
import share.io.input.InputFactory
import share.io.input.InputFactoryImpl
import share.io.input.logged.LoggedInputFactory
import share.io.output.OutputFactory
import share.io.output.OutputFactoryImpl
import share.io.output.command_result.CommandOutputFactory
import share.io.output.logged.LoggedOutputFactory
import share.security.ExchangeRole
import share.security.encoder.EncoderFactory
import share.security.encoder.LoggedAESDHEncoderFactory
import share.security.encoder_manager.ContextEncoderManager
import share.security.encoder_manager.ContextEncoderManagerImpl
import java.io.FileInputStream
import java.util.*
import javax.sql.ConnectionPoolDataSource

@DelicateCoroutinesApi
fun main(args: Array<String>) {

    val URL = "jdbc:postgresql://localhost:5432/mydb"

    val props = Properties()
    props.load(FileInputStream("D:\\Programming\\ITMO_KOTLIN\\lab5\\src\\main\\resources\\cfg\\db.cfg"))

    val buildersModule = DI.Module("buildersModule") {
        bind<MovieBuilder> { singleton(sync=false) { MovieBuilderImpl(instance(), instance(), instance()) }}
        bind<CoordinatesBuilder> { singleton(sync=false) { CoordinatesBuilderImpl() }}
        bind<PersonBuilder> { singleton(sync=false){ PersonBuilderImpl() }}
        bind<UserBuilder> { singleton(sync=false) { UserBuilderImpl() }}
    }

    val dbModule = DI.Module("dbModule"){
        bind<ConnectionPoolDataSource> { singleton(sync=false) {
            val pool = PGConnectionPoolDataSource()
            pool.setURL(URL)
            pool.serverNames = arrayOf("localhost")
            pool.databaseName = "mydb"
            pool.portNumbers = intArrayOf(5432)
            pool.user = props.getProperty("user") as String
            pool.password = props.getProperty("password") as String
            pool
        }}

        bind<SecondaryStorage<Coordinates>> { singleton(sync=false) { SecondaryMapStorage() }}
        bind<SecondaryStorage<Person>> { singleton(sync=false) { SecondaryMapStorage() }}
        bind<SecondaryStorage<Movie>> { singleton(sync=false) { SecondaryMapStorage() }}

        bind<EventManager> {singleton(sync=false) {
            EventManagerImpl(instance(), instance(), instance(), instance(), instance(), instance(), instance())
        }}

        bind<EventReader> {singleton(sync=false) { EventReaderImpl() }}

        bind<EventHandler> {singleton(sync=false) { EventHandlerImpl(URL, props, instance(), instance()) }}

        bind<Repository> { singleton(sync=false) {
            PostgresRepository(instance(), instance(), instance(), instance(), instance())
        }}
    }

    val di = DI.direct {
        import(dbModule)
        import(buildersModule)

        bind<Data> { singleton(sync=false){ LinkedHashSetData()}}

        bindSingleton<MainStorage>(sync=false) { RepositorySetMainStorage(instance(), instance()) }
        bindSingleton<StorageKeeper>(sync=false) { PostgreSQLStorageKeeper(instance(), instance(), instance(), instance()) }

        bind<StreamExecutor> {
            singleton(sync=false) { StreamExecutorImpl(instance(tag="connection"), instance()) }
        }

        bind<ContextEncoderManager> { singleton(sync=false) { ContextEncoderManagerImpl() }}

        bind {singleton(sync=false) { ExecutorImpl(instance()) }}

        bind<Executor> {
            singleton(sync=false) { CheckAccessExecutor(instance(), instance(), instance()) }
        }

        bindSingleton<CommandReader>(tag="connection", sync=false) { JSONReader() }

        bindSingleton(sync=false){
            CommandFromDTOMapperServer(instance(), instance(), instance(), instance())
        }

        bindSingleton<InputFactory>(sync=false){ LoggedInputFactory(InputFactoryImpl()) }
        bindSingleton<OutputFactory>(sync=false) { LoggedOutputFactory(OutputFactoryImpl()) }

        bindSingleton<OutputFactory>(sync=false, tag="command") {
            LoggedOutputFactory(CommandOutputFactory(OutputFactoryImpl()))
        }

        bindSingleton<EncoderFactory>(sync=false) { LoggedAESDHEncoderFactory(ExchangeRole.PARAMETERS_RECEIVER) }

        bindSingleton<Doorman>(sync=false) { DoormanImpl(instance()) }
        bindSingleton<UserManager>(sync=false) { PostgresUserManager(instance()) }

        bindSingleton<Server>(sync=false) {
            ServerImpl(instance(), instance(), instance(), instance(tag="command"), instance(), instance(), instance())
        }
    }
    runBlocking {
        launch(newSingleThreadContext("event-manager")) {
            val storageKeeper: StorageKeeper = di.instance()
            storageKeeper.open()
        }

        Thread.sleep(1000L)

        val server: Server = di.instance()
        server.start(6346)
    }
}