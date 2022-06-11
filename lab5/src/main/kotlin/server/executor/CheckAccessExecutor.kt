package server.executor

import org.slf4j.LoggerFactory
import server.database.repository.UserManager
import share.commands.dto.CommandDTO
import share.commands.dto.CommandType
import share.commands.util.CommandResult
import share.executor.Executor
import share.executor.ExecutorImpl
import share.security.encoder_manager.ContextEncoderManager
import share.util.NoPermissionsException

class CheckAccessExecutor(val executorImpl: ExecutorImpl,
                          val userManager: UserManager,
                          val encoderManager: ContextEncoderManager
): Executor{

    val logger = LoggerFactory.getLogger(this.javaClass)

    override fun execute(cmd: CommandDTO): CommandResult {
        if(cmd.type == CommandType.DISCONNECT){
            return CommandResult(true, "Disconnect")
        }
        val encoder = encoderManager.get()

        logger.info("Executing {} as {}", cmd.type.name, cmd.user.login)

        val user = cmd.user
        val password = encoder.decrypt(user.password)

        userManager.check(user.login, password.toByteArray(Charsets.UTF_8))
        return try {
            executorImpl.execute(cmd)
        } catch (e: NoPermissionsException) {
            CommandResult(false, e.message ?: "")
        }
    }

    override fun execute(cmdList: List<CommandDTO>): List<CommandResult> {
        val res = mutableListOf<CommandResult>()

        for(cmd in cmdList) {
            res.add(execute(cmd))
        }

        return res
    }

    override fun getHistory(): List<CommandDTO> {
        return executorImpl.getHistory()
    }
}