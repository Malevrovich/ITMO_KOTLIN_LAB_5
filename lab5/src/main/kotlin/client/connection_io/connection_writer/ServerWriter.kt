package client.connection_io.connection_writer

import share.commands.util.CommandDTO

interface ServerWriter {
    fun write(command: CommandDTO)
    fun write(msg: String?)
}