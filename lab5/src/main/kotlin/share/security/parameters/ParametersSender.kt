package share.security.parameters

import java.security.AlgorithmParameters

interface ParametersSender {
    fun send(params: AlgorithmParameters)
}