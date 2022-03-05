package commands.util

import commands.*
import data.movie.Movie
import executor.Executor
import executor.StreamExecutor
import org.kodein.di.DI
import org.kodein.di.instance
import storage.Storage
import storage.StorageKeeper

abstract class CommandFactory(di: DI) {
    private val config: CommandFactoryConfigure by di.instance()

    val storageKeeper: StorageKeeper by di.instance()

    open val currentStorage: Storage by storageKeeper
    open val streamExecutor: StreamExecutor by config
    open val currentExecutor: Executor by config

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