package share.security.parameters

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import share.io.output.Output
import java.security.AlgorithmParameters

class ParameterSenderImpl(val friendOutput: Output): ParametersSender {
    var actual: AlgorithmParameters? = null

    override fun send(params: AlgorithmParameters) {
        if(params != actual) {
            friendOutput.println(Json.encodeToString(params.encoded))
            actual = params
        }
    }
}