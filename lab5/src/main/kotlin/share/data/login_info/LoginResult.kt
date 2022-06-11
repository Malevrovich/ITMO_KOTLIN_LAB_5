package share.data.login_info

import kotlinx.serialization.Serializable

@Serializable
data class LoginResult(
    val msg: String?,
    val success: Boolean)
