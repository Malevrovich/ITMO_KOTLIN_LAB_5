package commands

import commands.util.CommandResult

abstract class Command(open val name: String) {
    abstract fun execute(): CommandResult
}