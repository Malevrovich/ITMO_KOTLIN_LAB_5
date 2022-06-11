package server.database.event

import org.slf4j.LoggerFactory
import java.util.*

class EventReaderImpl: EventReader {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    private val replace_reg = Regex("\\[.*?]")
    private val whitespace_reg = Regex(" (?=[a-zA-Z])")

    override fun read(str: String): Event {
        val s = str.replace(replace_reg, "")

        if(s.contains("TRUNCATE:")) {
            return Event(EventType.TRUNCATE, "movie", -1, Properties())
        }

        val arr = s.split(whitespace_reg)

        if(arr.size == 1){
            return Event(EventType.BEGIN, "movie", 0, Properties())
        }

        val table = arr[1].split(".")[1].trimEnd(':')

        val type = when(arr[2]){
            "INSERT:" -> EventType.INSERT
            "DELETE:" -> EventType.DELETE
            "UPDATE:" -> EventType.UPDATE
            else -> {
                throw IllegalArgumentException("Third word must contain type of event: " + arr[2])
            }
        }

        val id = arr[3].split(":")[1].toInt()

        val props = Properties()

        for(i in 4..(arr.size-1)){
            val key = arr[i].substringBefore(":")
            val value = arr[i].substringAfter(":").substringBefore("+").substringBefore(".").trim('\'')

            logger.debug("Setting property: {} = {}", key, value)

            if(value == "null") {
                continue
            }

            props.setProperty(key, value)
        }

        return Event(type, table, id, props)
    }
}