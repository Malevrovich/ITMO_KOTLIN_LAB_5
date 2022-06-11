package share.security.encoder

import share.io.input.Input
import share.io.output.Output

interface EncoderFactory {
    fun buildCryptoManager(friendInput: Input, friendOutput: Output): Encoder
}