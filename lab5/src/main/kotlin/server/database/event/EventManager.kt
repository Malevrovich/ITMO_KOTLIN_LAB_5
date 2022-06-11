package server.database.event

interface EventManager {
    fun process(event: Event)
    fun init()
}