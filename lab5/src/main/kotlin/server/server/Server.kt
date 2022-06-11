package server.server

interface Server {
    suspend fun start(port: Int)
}