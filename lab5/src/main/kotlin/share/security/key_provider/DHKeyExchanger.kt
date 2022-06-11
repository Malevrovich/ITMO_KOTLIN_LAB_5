package share.security.key_provider

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import share.io.input.Input
import share.io.output.Output
import share.security.ExchangeRole
import java.security.KeyFactory
import java.security.KeyPairGenerator
import java.security.PublicKey
import java.security.spec.X509EncodedKeySpec
import javax.crypto.KeyAgreement
import javax.crypto.interfaces.DHPublicKey

open class DHKeyExchanger(val role: ExchangeRole,
                          val friendInput: Input,
                          val friendOutput: Output): KeyExchanger {

    val keyAgreement = KeyAgreement.getInstance("DH")
    lateinit var publicKey: PublicKey

    lateinit var secretKey: ByteArray

    private fun initWith(keyPairGenerator: KeyPairGenerator) {
        val keyPair = keyPairGenerator.generateKeyPair()

        publicKey = keyPair.public
        keyAgreement.init(keyPair.private)
    }

    init {
        if(role == ExchangeRole.PARAMETERS_SENDER){
            val keyPairGenerator = KeyPairGenerator.getInstance("DH")
            keyPairGenerator.initialize(2048)

            initWith(keyPairGenerator)
        }
    }

    override fun sendPublicKey() {
        friendOutput.println(Json.encodeToString(publicKey.encoded))
    }

    override fun acceptPublicKey() {
        val key: ByteArray = Json.decodeFromString(friendInput.nextLine())

        val keyFactory = KeyFactory.getInstance("DH")
        val x509keySpec = X509EncodedKeySpec(key)

        val othersPublicKey = keyFactory.generatePublic(x509keySpec)

        if(role == ExchangeRole.PARAMETERS_RECEIVER) {
            val params = (othersPublicKey as DHPublicKey).params

            val keyPairGenerator = KeyPairGenerator.getInstance("DH")
            keyPairGenerator.initialize(params)

            initWith(keyPairGenerator)
        }

        keyAgreement.doPhase(othersPublicKey, true)
    }

    override fun getKey(): ByteArray {
        if(!this::secretKey.isInitialized){
            processKey()
            secretKey = keyAgreement.generateSecret()
        }

        return secretKey
    }

    override fun processKey() {
        if(role == ExchangeRole.PARAMETERS_SENDER) {
            sendPublicKey()
            acceptPublicKey()
        } else{
            acceptPublicKey()
            sendPublicKey()
        }
    }
}