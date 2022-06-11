package client.io.connection_input

import client.io.connection.Connection
import share.io.input.Input
import java.io.IOException
import java.rmi.ConnectIOException

class ConnectionInput(val connection: Connection): Input {
    var pos = 0
    var buffer = ""

    private fun loadBuffer(){
        try {
            buffer = connection.read()
            pos = 0
        } catch (e: IOException) {
            throw ConnectIOException("Connection failed", e)
        }
    }

    override fun nextLine(): String = synchronized(this) {
        if(buffer.length <= pos) {
            loadBuffer()
        } else {
            buffer = buffer.substring(pos)
            pos = 0
        }

        val stringBuilder = StringBuilder()

        do {
            val curLine = buffer.substringBefore('\n', buffer)

            stringBuilder.append(curLine)

            if(buffer.length - 1 == curLine.length) {
                break
            }

            loadBuffer()
        } while (curLine != "")
        pos = buffer.indexOf('\n') + 1

        return stringBuilder.toString()
    }

    override fun read(): Char {
        if(pos == buffer.length) {
            loadBuffer()
        }
        return buffer[pos++]
    }

    override fun ready(): Boolean {
        return connection.readyForRead()
    }

}