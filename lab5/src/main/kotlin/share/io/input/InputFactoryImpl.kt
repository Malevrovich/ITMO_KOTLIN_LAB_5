package share.io.input

import java.io.InputStream

class InputFactoryImpl : InputFactory {
    override fun buildInput(inputStream: InputStream): Input {
        return StreamInput(inputStream)
    }

    override fun buildInput(input: Input): Input {
        return input
    }
}