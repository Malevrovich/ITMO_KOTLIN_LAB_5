package share.io.input.logged

import share.io.input.Input
import share.io.input.InputFactory
import java.io.InputStream

class LoggedInputFactory(val inputFactory: InputFactory): InputFactory {
    override fun buildInput(inputStream: InputStream): Input {
        return LoggedInput(inputFactory.buildInput(inputStream))
    }

    override fun buildInput(input: Input): Input {
        return LoggedInput(input)
    }
}