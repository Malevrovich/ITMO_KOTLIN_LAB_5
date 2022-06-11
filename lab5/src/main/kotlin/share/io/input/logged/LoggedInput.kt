package share.io.input.logged

import org.slf4j.LoggerFactory
import share.io.input.Input

class LoggedInput(val input: Input): Input {

    val logger = LoggerFactory.getLogger(input.javaClass)

    override fun nextLine(): String {
        val res = input.nextLine()
        logger.info("Received line: $res")
        return res
    }

    override fun read(): Char {
        val res = input.read()
        logger.info("Received char: $res")
        return res
    }

    override fun ready(): Boolean {
        return input.ready()
    }

}