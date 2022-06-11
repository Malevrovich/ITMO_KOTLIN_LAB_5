package share.io.input

import java.io.IOException
import java.io.InputStream
import java.net.SocketTimeoutException

class StreamInput(input: InputStream): Input {
    val bufferedReader = input.bufferedReader(Charsets.UTF_8)

    override fun nextLine(): String {
        try {
            return bufferedReader.readLine()
        } catch (e: SocketTimeoutException){
            throw IOException("Connection timeout")
        }
    }

    override fun read(): Char {
        try {
            return bufferedReader.read().toChar()
        } catch (e: SocketTimeoutException){
            throw IOException("Connection timeout")
        }
    }

    override fun ready(): Boolean {
        return bufferedReader.ready()
    }
}