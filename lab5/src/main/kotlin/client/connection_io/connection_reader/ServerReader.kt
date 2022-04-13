package client.connection_io.connection_reader

import share.commands.util.CommandResult

interface ServerReader {
    fun readCommandResult(): CommandResult
}