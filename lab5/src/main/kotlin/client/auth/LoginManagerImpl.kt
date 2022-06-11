package client.auth

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import share.data.login_info.LoginEvent
import share.data.login_info.LoginEventType
import share.data.login_info.LoginResult
import share.data.user.User
import share.data.user.UserBuilder
import share.io.input.Input
import share.io.output.Output
import share.security.encoder.Encoder

class LoginManagerImpl(private val serverOutput: Output,
                       private val serverInput: Input,
                       private val userBuilder: UserBuilder,
                       private val encoder: Encoder
): LoginManager {

    lateinit var currentUser: User

    private fun processLoginEvent(login: String, password: String, type: LoginEventType){
        val user = userBuilder.clear()
            .setLogin(login)
            .setPassword(encoder.encrypt(password))
            .build()

        val loginEvent = LoginEvent(user, type)

        serverOutput.println(Json.encodeToString(loginEvent))

        val loginResult = Json.decodeFromString<LoginResult>(serverInput.nextLine())

        if(!loginResult.success){
            throw IllegalArgumentException(loginResult.msg)
        }

        currentUser = user
    }

    override fun signIn(login: String, password: String) {
        processLoginEvent(login, password, LoginEventType.LOGIN)
    }

    override fun signUp(login: String, password: String) {
        processLoginEvent(login, password, LoginEventType.REGISTER)
    }

    override fun disconnect() {
        val loginEvent = LoginEvent(userBuilder.buildDefault(), LoginEventType.DISCONNECT)
        serverOutput.println(Json.encodeToString(loginEvent))
    }

    override fun getCurrentSession(): User {
        return currentUser
    }
}