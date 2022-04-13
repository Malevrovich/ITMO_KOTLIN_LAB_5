package client.connection_io.connection_writer

import client.connection_io.connection.Connection
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import share.commands.util.CommandDTO
import java.io.IOException

class ServerWriterImpl(private val connection: Connection): ServerWriter {
    override fun write(command: CommandDTO) {
        val msg = Json.encodeToString(command) + "\n"
        write(msg)
    }

    override fun write(msg: String?) {
        if(msg == null){
            return
        }
        if(connection.isConnected()){
            connection.write(msg.toByteArray())
        } else{
            throw IOException("connection failed")
        }
    }
}