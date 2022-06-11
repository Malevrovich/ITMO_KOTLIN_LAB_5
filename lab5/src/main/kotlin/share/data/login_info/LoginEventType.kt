package share.data.login_info

import kotlinx.serialization.Serializable

@Serializable
enum class LoginEventType {
    REGISTER, LOGIN, DISCONNECT
}