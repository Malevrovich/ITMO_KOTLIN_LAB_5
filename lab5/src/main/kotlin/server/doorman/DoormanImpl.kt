package server.doorman

import com.sun.jdi.connect.spi.ClosedConnectionException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.slf4j.LoggerFactory
import server.database.repository.UserManager
import share.data.login_info.LoginEvent
import share.data.login_info.LoginEventType
import share.data.login_info.LoginResult
import share.io.input.Input
import share.io.output.Output
import share.security.encoder.Encoder
import java.sql.SQLException
import javax.security.auth.login.LoginException

class DoormanImpl(val userManager: UserManager): Doorman {

    val logger = LoggerFactory.getLogger(this.javaClass)

    override fun greet(clientInput: Input, clientOutput: Output, encoder: Encoder) {
        while(true) {
            logger.info("Reading loginEvent")
            val loginEvent: LoginEvent = Json.decodeFromString(clientInput.nextLine())

            if(loginEvent.type == LoginEventType.DISCONNECT){
                throw ClosedConnectionException()
            }

            val login = loginEvent.user.login
            val password = encoder.decrypt(loginEvent.user.password)

            logger.debug("Decrypted password: \"{}\"", password)

            if(loginEvent.type == LoginEventType.REGISTER) {
                try {
                    logger.info("Registration...")
                    userManager.register(login, password.toByteArray(Charsets.UTF_8))
                } catch (e: SQLException) {
                    logger.info("Registration failed {}", e.message)

                    clientOutput.println(
                        Json.encodeToString(
                            LoginResult(e.message, false)
                        )
                    )
                    logger.debug("Result sent")
                    continue
                } catch (e: Exception) {
                    logger.error("Registration failed {}", e.message)
                    throw e
                }
            } else{
                try {
                    logger.info("Entering...")
                    userManager.check(login, password.toByteArray(Charsets.UTF_8))
                } catch (e: LoginException) {
                    logger.info("Login failed {}", e.message)

                    clientOutput.println(
                        Json.encodeToString(
                            LoginResult(e.message, false)
                        )
                    )
                    continue
                } catch (e: Exception){
                    logger.error(e.message)
                    throw e
                }
            }

            logger.info("Success")
            clientOutput.println(
                Json.encodeToString(
                    LoginResult(null, true)
                )
            )
            break
        }
    }
}