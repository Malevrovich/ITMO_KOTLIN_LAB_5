package client.executor

import client.io.connection.Connection
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import share.commands.dto.CommandDTO
import share.commands.dto.CommandType
import share.commands.util.CommandResult
import share.executor.BasicExecutor
import share.io.input.Input
import share.io.output.Output

class ServerExecutor(val connection: Connection,
                     val serverInput: Input,
                     val serverOutput: Output
)
    : BasicExecutor() {

    override fun execute(cmd: CommandDTO): CommandResult {
        serverOutput.println(Json.encodeToJsonElement(cmd))

        if(cmd.type == CommandType.DISCONNECT) {
            return CommandResult(true, "DISCONNECT")
        }
        return Json.decodeFromString(serverInput.nextLine())
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