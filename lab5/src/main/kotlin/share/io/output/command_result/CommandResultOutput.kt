package share.io.output.command_result

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import share.commands.util.CommandResult
import share.io.output.Output

class CommandResultOutput(val output: Output): Output {
    override fun print(x: Any?) {
        output.println(Json.encodeToJsonElement(CommandResult(false, x.toString())))
    }

    override fun println(x: Any?) {
        output.println(Json.encodeToJsonElement(CommandResult(false, x.toString())))
    }
}