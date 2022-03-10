package executor

import commands.Command
import commands.util.CommandResult
import java.util.*

class ExecutorImpl: Executor{
    private val executed: MutableList<Command> = mutableListOf()

    private fun addToHistory(cmd: Command){
        executed.add(cmd)
        if(executed.size > 8) {
            executed.removeAt(0)
        }
    }

    override fun getHistory(): List<Command> {
        return executed
    }

    override fun execute(cmd: Command): CommandResult {
        val res = cmd.execute()
        addToHistory(cmd)
        return res
    }

    override fun execute(cmdList: List<Command>): List<CommandResult> {
        val res = LinkedList<CommandResult>()

        for(cmd in cmdList){
            val cmdRes = execute(cmd)

            res.add(cmdRes)

            if(cmdRes.stop){
                break
            }
        }

        return res
    }
}