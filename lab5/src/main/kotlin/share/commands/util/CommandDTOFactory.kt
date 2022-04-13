package share.commands.util

import share.data.movie.Movie

interface CommandDTOFactory {
    fun buildHelp(): CommandDTO
    fun buildInfo(): CommandDTO
    fun buildShow(): CommandDTO
    fun buildUpdate(id: Int, el: Movie): CommandDTO
    fun buildAdd(el: Movie): CommandDTO
    fun buildRemove(id: Int): CommandDTO
    fun buildClear(): CommandDTO
    fun buildSave(): CommandDTO
    fun buildExecuteFile(filename: String): CommandDTO
    fun buildExit(): CommandDTO
    fun buildAddIfMin(el: Movie): CommandDTO
    fun buildRemoveGreater(el: Movie): CommandDTO
    fun buildHistory(): CommandDTO
    fun buildSumOfLength(): CommandDTO
    fun buildPrintUniqueGenre(): CommandDTO
    fun buildPrintFieldDescendingScreenwriter(): CommandDTO
}