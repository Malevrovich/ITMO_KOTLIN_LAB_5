package share.executor

import org.slf4j.LoggerFactory
import share.commands.dto.CommandDTO
import share.commands.dto.CommandFromDTOMapper
import share.commands.util.CommandResult
import share.executor.BasicExecutor
import java.util.*

class ExecutorImpl(private val mapper: CommandFromDTOMapper) : BasicExecutor() {
    override fun execute(cmd: CommandDTO): CommandResult {
        return try {
            val res = mapper.fromDTO(cmd, this).execute()
            addToHistory(cmd)
            res
        } catch (e: IllegalArgumentException) {
            CommandResult(false, e.message ?: "Something wrong with mapper")
        }
    }

    override fun execute(cmdList: List<CommandDTO>): List<CommandResult> {
        val res = LinkedList<CommandResult>()

        for (cmd in cmdList) {
            val cmdRes = execute(cmd)

            res.add(cmdRes)

            if (cmdRes.stop) {
                break
            }
        }

        return res
    }
}