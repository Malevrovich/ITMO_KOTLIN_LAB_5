package server.database.event

interface EventReader {
    fun read(str: String): Event
}
