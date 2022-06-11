package client.auth

import share.io.input.Input
import share.io.output.Output

interface AuthenticateReader {
    fun auth(input: Input, output: Output)
}