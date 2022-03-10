package commands.util

import commands.*
import data.movie.Movie
import executor.Executor
import executor.StreamExecutor
import executor.StreamExecutorKeeper
import org.kodein.di.DI
import org.kodein.di.instance
import storage.Storage
import storage.StorageKeeper

abstract class CommandFactory(
    val storageKeeper: StorageKeeper,
    open val currentStorage: Storage,
    open val executor: Executor,
    val streamExecutorKeeper: StreamExecutorKeeper
) {
    abstract fun buildHelp(): HelpCmd
    abstract fun buildInfo(): InfoCmd
    abstract fun buildShow(): ShowCmd
    abstract fun buildUpdate(id: Int, el: Movie): UpdateCmd
    abstract fun buildAdd(el: Movie): AddCmd
    abstract fun buildRemove(id: Int): RemoveCmd
    abstract fun buildClear(): ClearCmd
    abstract fun buildSave(): SaveCmd
    abstract fun buildExecuteFile(filename: String): ExecuteFileCmd
    abstract fun buildExit(): ExitCmd
    abstract fun buildAddIfMin(el: Movie): AddIfMinCmd
    abstract fun buildRemoveGreater(el: Movie): RemoveGreaterCmd
    abstract fun buildHistory(): HistoryCmd
    abstract fun buildSumOfLength(): SumOfLength
    abstract fun buildPrintUniqueGenre(): PrintUniqueGenreCmd
    abstract fun buildPrintFieldDescendingScreenwriter(): PrintFieldDescendingScreenwriterCmd
}