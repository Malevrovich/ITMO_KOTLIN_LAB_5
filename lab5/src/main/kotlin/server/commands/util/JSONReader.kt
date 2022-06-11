package server.commands.util

import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.slf4j.LoggerFactory
import share.commands.dto.CommandDTO
import share.commands.util.CommandReader
import share.io.input.Input
import share.io.output.Output
import share.util.ParseException

class JSONReader: CommandReader {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun readCommand(inp: Input, output: Output): CommandDTO {
        logger.info("Reading CommandDTO")
        try{
            return Json.decodeFromString(inp.nextLine())
        } catch (e: SerializationException){
            logger.warn("Wrong JSON")
            throw ParseException("Expected correct json")
        }
    }
}