package share.security.encoder

import share.security.key_provider.KeyProvider
import share.security.parameters.ParametersReceiver
import share.security.parameters.ParametersSender
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

class AESDHEncoder(val keyProvider: KeyProvider,
                   val parametersSender: ParametersSender,
                   val parametersReceiver: ParametersReceiver
): Encoder {

    lateinit var aesKey: SecretKey

    val encryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
    val decryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding")

    override fun init() {
        aesKey = SecretKeySpec(keyProvider.getKey(), 0, 16, "AES")

        encryptCipher.init(Cipher.ENCRYPT_MODE, aesKey)
        parametersSender.send(encryptCipher.parameters)

        val params = parametersReceiver.receive()
        decryptCipher.init(Cipher.DECRYPT_MODE, aesKey, params)
    }

    override fun encrypt(msg: String): ByteArray {
        return encryptCipher.doFinal(msg.toByteArray())
    }

    override fun decrypt(msg: ByteArray): String {
        return String(decryptCipher.doFinal(msg), Charsets.UTF_8)
    }

}