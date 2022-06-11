package share.io.output.logged

import org.slf4j.LoggerFactory
import share.io.output.Output

class LoggedOutput(val output: Output): Output {
    val logger = LoggerFactory.getLogger(output.javaClass)

    override fun print(x: Any?) {
        logger.info("Printing " + x.toString())
        output.print(x)
    }

    override fun println(x: Any?) {
        logger.info("Printing " + x.toString() + "\\n")
        output.println(x)
    }

}