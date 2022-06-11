package client

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import client.auth.LoginManager
import client.auth.LoginManagerImpl
import client.commands.dto.CommandDTOFactory
import client.commands.dto.CommandDTOFactoryClient
import client.executor.ServerExecutor
import client.io.connection.Connection
import client.io.connection.SocketChannelConnection
import client.io.connection_input.ConnectionInput
import client.io.connection_output.ConnectionOutput
import client.storage.ExecutorStateFlowHolder
import client.storage.StateFlowHolder
import client.ui.pages.auth.authPage
import client.ui.pages.main.mainPage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.compose.withDI
import org.kodein.di.instance
import share.data.coordinates.CoordinatesBuilder
import share.data.coordinates.CoordinatesBuilderImpl
import share.data.movie.MovieBuilder
import share.data.movie.MovieBuilderImpl
import share.data.person.PersonBuilder
import share.data.person.PersonBuilderImpl
import share.data.user.UserBuilder
import share.data.user.UserBuilderImpl
import share.executor.Executor
import share.io.input.Input
import share.io.output.Output
import share.localization.Localization
import share.localization.LocalizationImpl
import share.security.ExchangeRole
import share.security.encoder.AESDHEncoder
import share.security.encoder.Encoder
import share.security.key_provider.DHKeyExchanger
import share.security.key_provider.KeyProvider
import share.security.parameters.ParameterSenderImpl
import share.security.parameters.ParametersReceiver
import share.security.parameters.ParametersReceiverImpl
import share.security.parameters.ParametersSender
import java.net.InetAddress
import java.net.InetSocketAddress

fun main(){
    val addr = InetSocketAddress(InetAddress.getLocalHost(), 6346)

    val di = DI {
        // Builders
        bindSingleton<CoordinatesBuilder>(sync=false) { CoordinatesBuilderImpl() }
        bindSingleton<PersonBuilder>(sync=false) { PersonBuilderImpl() }
        bindSingleton<MovieBuilder>(sync=false) { MovieBuilderImpl(instance(), instance(), instance()) }
        bindSingleton<UserBuilder>(sync=false) { UserBuilderImpl() }

        // Commands, Executing, etc.
        bindSingleton<CommandDTOFactory>(sync=false) { CommandDTOFactoryClient(instance()) }

        bindSingleton<Executor> {
            ServerExecutor(instance(), instance(), instance())
        }

        //IO
        bindSingleton<Connection> { SocketChannelConnection() }

        bindSingleton<Input> { ConnectionInput(instance()) }
        bindSingleton<Output> { ConnectionOutput(instance()) }

        // Authentication
        bindSingleton<LoginManager> {
            LoginManagerImpl(instance(), instance(), instance(), instance())
        }

        // Security
        bindSingleton<Encoder> { AESDHEncoder(instance(), instance(), instance()) }

        bindSingleton<KeyProvider> {
            DHKeyExchanger(ExchangeRole.PARAMETERS_SENDER, instance(), instance())
        }
        bindSingleton<ParametersReceiver> { ParametersReceiverImpl(instance()) }
        bindSingleton<ParametersSender> { ParameterSenderImpl(instance()) }

        // Data
        bindSingleton<StateFlowHolder> { ExecutorStateFlowHolder(instance(), instance()) }

        // Localization
        bindSingleton<Localization> { LocalizationImpl() }
    }

    val conn: Connection by di.instance()
    conn.connect(addr)

    val encoder: Encoder by di.instance()
    encoder.init()

    application(exitProcessOnExit = false) {
        Window(
            onCloseRequest = {
                val loginManager: LoginManager by di.instance()
                loginManager.disconnect()
                exitApplication() },
            state = WindowState(width = 800.dp, height = 600.dp)
        ) {
            withDI(di) {
                val isAuth = remember { mutableStateOf(false) }
                if (!isAuth.value) {
                    authPage(
                        onSuccess = {
                            isAuth.value = true
                        }
                    )
                } else {
                    rememberCoroutineScope().launch(Dispatchers.Default){
                        val stateHolder: StateFlowHolder by di.instance()
                        stateHolder.startListen()
                    }
                    mainPage(
                        onExitClick = {
                            val commandDTOFactory: CommandDTOFactory by di.instance()
                            val executor: Executor by di.instance()

                            executor.execute(commandDTOFactory.buildDisconnect())
                            exitApplication()
                        }
                    )
                }
            }
        }
    }
}