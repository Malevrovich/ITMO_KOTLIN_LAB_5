package server.database.event

import java.util.*

data class Event(
    val type: EventType,
    val table: String,
    val id: Int,
    val props: Properties
)
