package share.io.input

import java.io.InputStream

interface InputFactory{
    fun buildInput(inputStream: InputStream): Input
    fun buildInput(input: Input): Input
}