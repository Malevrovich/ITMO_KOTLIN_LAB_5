package client.connection_io.connection

interface Connection {
    fun isConnected(): Boolean
    fun reconnect()
    fun read(): String
    fun write(byteArray: ByteArray)
    fun close()
}