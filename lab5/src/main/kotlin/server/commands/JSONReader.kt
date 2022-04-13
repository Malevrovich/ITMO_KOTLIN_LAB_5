package server.commands

import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import share.commands.util.CommandDTO
import share.commands.util.CommandReader
import share.user_io.user_reader.UserReader
import share.user_io.user_writer.UserWriter
import share.util.ParseException

class JSONReader: CommandReader {
    override fun readCommand(inp: UserReader, out: UserWriter): CommandDTO {
        try{
            return Json.decodeFromString(inp.nextLine())
        } catch (e: SerializationException){
            throw ParseException("Expected correct json")
        }
    }
}