package share.security.parameters

import java.security.AlgorithmParameters

interface ParametersReceiver {
    fun receive(): AlgorithmParameters?
}