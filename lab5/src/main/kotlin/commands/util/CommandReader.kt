package commands.util

import commands.Command
import java.io.PrintStream
import java.util.*

interface CommandReader {
    fun readCommand(inp: Scanner, out: PrintStream): Command?
}