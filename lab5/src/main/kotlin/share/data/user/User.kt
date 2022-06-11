package share.data.user

import kotlinx.serialization.Serializable

@Serializable
data class User(val login: String,
                val password: ByteArray)
