package client.io.connection

import java.io.IOException
import java.net.ConnectException
import java.net.InetSocketAddress
import java.nio.Buffer
import java.nio.ByteBuffer
import java.nio.channels.SelectionKey
import java.nio.channels.Selector
import java.nio.channels.SocketChannel

class SocketChannelConnection : Connection {
    private lateinit var socketChannel: SocketChannel
    private val readSelector: Selector = Selector.open()
    lateinit var currentAddr: InetSocketAddress

    override fun connect(addr: InetSocketAddress) {
        while(true) {
            try {
                socketChannel = SocketChannel.open(addr)
            } catch (e: ConnectException) {
                println(e.message)
                Thread.sleep(1000)
                continue
            }
            break
        }
        while(!socketChannel.finishConnect()){
            Thread.sleep(100L)
            println("Connecting...")
        }
        socketChannel.configureBlocking(false)
        socketChannel.register(readSelector, SelectionKey.OP_READ)
    }

    override fun isConnected(): Boolean {
        return socketChannel.socket().isConnected
    }

    override fun reconnect() {
        socketChannel.close()

        while(true) {
            try {
                socketChannel = SocketChannel.open(currentAddr)
            } catch (e: ConnectException) {
                continue
            }
            break
        }
        socketChannel.configureBlocking(false)


        while(!socketChannel.finishConnect()){
            print("Reconnecting...")
            Thread.sleep(100)
        }
    }

    override fun read(): String {
        val buffer: ByteBuffer = ByteBuffer.allocate(1024)
        val stringBuffer = StringBuffer()

        readSelector.select()

        val channel = readSelector.selectedKeys().first().channel() as SocketChannel

        while(channel.read(buffer) > 0){
            buffer.myFlip()
            stringBuffer.append(Charsets.UTF_8.decode(buffer))
            (buffer as Buffer).clear()

            if(!channel.socket().isConnected){
                throw IOException("connection failed")
            }
        }

        return stringBuffer.toString()
    }

    override fun write(byteArray: ByteArray) {
        val buffer = ByteBuffer.wrap(byteArray)

        if(!socketChannel.isConnected){
            throw IOException("connection failed")
        }
        socketChannel.write(buffer)
    }

    override fun close() {
        readSelector.close()
        socketChannel.close()
    }

    override fun readyForRead(): Boolean {
        return (readSelector.selectNow() != 0)
    }
}

private fun Buffer.myFlip(): Buffer {
    limit(position())
    mark()
    position(0)
    return this
}
