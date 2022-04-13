package share.executor

import share.commands.util.CommandDTO

abstract class BasicExecutor: Executor {
    private val executed: MutableList<CommandDTO> = mutableListOf()

    open fun addToHistory(cmd: CommandDTO){
        executed.add(cmd)
        if(executed.size > 8) {
            executed.removeAt(0)
        }
    }

    override fun getHistory(): List<CommandDTO> {
        return executed
    }
}