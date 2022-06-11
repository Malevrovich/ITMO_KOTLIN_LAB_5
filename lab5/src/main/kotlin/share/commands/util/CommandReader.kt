package share.commands.util

import share.commands.dto.CommandDTO
import share.io.input.Input
import share.io.output.Output

interface CommandReader {
    fun readCommand(inp: Input, output: Output): CommandDTO
}