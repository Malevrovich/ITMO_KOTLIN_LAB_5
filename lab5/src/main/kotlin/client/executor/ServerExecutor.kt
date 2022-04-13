package client.executor

import client.connection_io.connection.Connection
import client.connection_io.connection_reader.ServerReader
import client.connection_io.connection_writer.ServerWriter
import share.commands.util.CommandResult
import share.commands.util.CommandDTO
import share.commands.util.CommandType
import share.executor.BasicExecutor
import share.user_io.user_reader.UserReader
import share.user_io.user_writer.UserWriter
import java.io.IOException

class ServerExecutor(val connection: Connection,
                     val serverReader: ServerReader,
                     val serverWriter: ServerWriter,
                     val userReader: UserReader,
                     val userWriter: UserWriter
)
    : BasicExecutor() {

    override fun execute(cmd: CommandDTO): CommandResult {
        if(cmd.type == CommandType.EXIT){
            return CommandResult(true, "Goodbye!")
        }

        while (true){
            try{
                serverWriter.write(cmd)
                return serverReader.readCommandResult()
            } catch (e: IOException){
                connection.reconnect()
            }
        }
    }

    override fun execute(cmdList: List<CommandDTO>): List<CommandResult> {
        val res = mutableListOf<CommandResult>()
        for(cmd in cmdList){
            res.add(execute(cmd))

            if(res.last().stop){
                break
            }
        }
        return res
    }
}