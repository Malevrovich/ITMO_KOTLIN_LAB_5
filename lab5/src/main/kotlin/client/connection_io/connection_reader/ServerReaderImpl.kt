package client.connection_io.connection_reader

import client.connection_io.connection.Connection
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import share.commands.util.CommandResult
import java.io.IOException

class ServerReaderImpl(val connection: Connection): ServerReader {

    override fun readCommandResult(): CommandResult {
        if(!connection.isConnected()){
            throw IOException("connection failed")
        }
        val string = connection.read()
        return Json.decodeFromString(string)
    }
}