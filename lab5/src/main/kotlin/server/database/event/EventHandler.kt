package server.database.event

interface EventHandler {
    fun start()
    fun stop()
}