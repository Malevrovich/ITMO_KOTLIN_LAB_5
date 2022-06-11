package share.data.login_info

import kotlinx.serialization.Serializable
import share.data.user.User

@Serializable
data class LoginEvent(val user: User,
                      val type: LoginEventType)
