package server.doorman

import share.io.input.Input
import share.io.output.Output
import share.security.encoder.Encoder

interface Doorman {
    fun greet(clientInput: Input, clientOutput: Output, encoder: Encoder)
}