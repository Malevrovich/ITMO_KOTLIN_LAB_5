package share.security.encoder

interface Encoder {
    fun init()

    fun encrypt(msg: String): ByteArray
    fun decrypt(msg: ByteArray): String
}