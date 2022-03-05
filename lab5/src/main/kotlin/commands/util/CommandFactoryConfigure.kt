package commands.util

import executor.Executor
import executor.StreamExecutor
import org.kodein.di.DI
import kotlin.reflect.KProperty

class CommandFactoryConfigure() {
    var currentExecutor: Executor? = null
    var streamExecutor: StreamExecutor? = null

    inline operator fun <reified T> getValue(commandFactory: CommandFactory, property: KProperty<*>): T{
        return if(currentExecutor is T) {
            (currentExecutor as T)?: throw NullPointerException("you should configure CommandFactory before creating")
        } else{
            if(streamExecutor is T){
                (streamExecutor as T)?: throw NullPointerException("you should configure CommandFactory before creating")
            } else {
                throw TypeCastException(
                    "type ${T::class.qualifiedName} can't be cast " +
                            "to ${Executor::class.qualifiedName} or ${StreamExecutor::class.qualifiedName}"
                )
            }
        }
    }
}