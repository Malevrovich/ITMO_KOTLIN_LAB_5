package commands.util

import commands.*
import data.movie.Movie
import org.kodein.di.DI

class CommandFactoryImpl(di: DI): CommandFactory(di) {
    override fun buildHelp(): HelpCmd {
        return HelpCmd()
    }

    override fun buildInfo(): InfoCmd {
        return InfoCmd(currentStorage)
    }

    override fun buildShow(): ShowCmd {
        return ShowCmd(currentStorage)
    }

    override fun buildUpdate(id: Int, el: Movie): UpdateCmd {
        return UpdateCmd(id, el, currentStorage)
    }

    override fun buildAdd(el: Movie): AddCmd {
        return AddCmd(el, currentStorage)
    }

    override fun buildRemove(id: Int): RemoveCmd {
        return RemoveCmd(id, currentStorage)
    }

    override fun buildClear(): ClearCmd {
        return ClearCmd(currentStorage)
    }

    override fun buildSave(): SaveCmd {
        return SaveCmd(storageKeeper)
    }

    override fun buildExecuteFile(filename: String): ExecuteFileCmd {
        return ExecuteFileCmd(filename, streamExecutor, currentExecutor)
    }

    override fun buildExit(): ExitCmd {
        return ExitCmd()
    }

    override fun buildAddIfMin(el: Movie): AddIfMinCmd {
        return AddIfMinCmd(el, currentStorage)
    }

    override fun buildRemoveGreater(el: Movie): RemoveGreaterCmd {
        return RemoveGreaterCmd(el, currentStorage)
    }

    override fun buildHistory(): HistoryCmd {
        return HistoryCmd(currentExecutor)
    }

    override fun buildSumOfLength(): SumOfLength {
        return SumOfLength(currentStorage)
    }

    override fun buildPrintUniqueGenre(): PrintUniqueGenreCmd {
        return PrintUniqueGenreCmd(currentStorage)
    }

    override fun buildPrintFieldDescendingScreenwriter(): PrintFieldDescendingScreenwriterCmd {
        return PrintFieldDescendingScreenwriterCmd(currentStorage)
    }

}