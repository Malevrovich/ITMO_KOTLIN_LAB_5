package server.commands.cmds

import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import server.storage.main.MainStorage
import share.commands.dto.CommandType
import share.commands.util.CommandResult
import share.data.movie.Movie

class GetAllCmd(val storage: MainStorage): Command("GetAll", CommandType.GET_ALL) {
    override fun execute(): CommandResult {
        return CommandResult(
            false,
            Json.encodeToString(ListSerializer(Movie.serializer()), storage.getAll())
        )
    }

}