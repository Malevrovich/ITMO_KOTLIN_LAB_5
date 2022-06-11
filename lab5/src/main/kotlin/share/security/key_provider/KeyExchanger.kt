package share.security.key_provider

interface KeyExchanger: KeyProvider {
    fun sendPublicKey()
    fun acceptPublicKey()


    override fun getKey(): ByteArray
    override fun processKey()
}