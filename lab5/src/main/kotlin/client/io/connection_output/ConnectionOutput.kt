package client.io.connection_output

import client.io.connection.Connection
import share.io.output.Output

class ConnectionOutput(val connection: Connection): Output {
    override fun print(x: Any?) {
        connection.write(x.toString().toByteArray(Charsets.UTF_8))
    }

    override fun println(x: Any?) {
        this.print(x)
        this.print("\n")
    }
}