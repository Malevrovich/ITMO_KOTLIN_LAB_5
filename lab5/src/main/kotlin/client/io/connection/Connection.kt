package client.io.connection

import java.net.InetSocketAddress

interface Connection {
    fun connect(addr: InetSocketAddress)

    fun isConnected(): Boolean
    fun reconnect()

    fun readyForRead(): Boolean
    fun read(): String

    fun write(byteArray: ByteArray)

    fun close()

}