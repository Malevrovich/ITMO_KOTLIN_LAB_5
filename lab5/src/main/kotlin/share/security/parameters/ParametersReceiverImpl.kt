package share.security.parameters

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import share.io.input.Input
import java.security.AlgorithmParameters

class ParametersReceiverImpl(val friendInput: Input): ParametersReceiver {
    override fun receive(): AlgorithmParameters? {
        val bytes: ByteArray = Json.decodeFromString(friendInput.nextLine())

        val params = AlgorithmParameters.getInstance("AES")
        params.init(bytes)

        return params
    }
}