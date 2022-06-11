package share.security.key_provider

interface KeyProvider {
    fun getKey(): ByteArray

    fun processKey()
}