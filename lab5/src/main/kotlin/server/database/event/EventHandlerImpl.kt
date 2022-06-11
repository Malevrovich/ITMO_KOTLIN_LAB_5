package server.database.event

import org.postgresql.PGConnection
import org.postgresql.PGProperty
import org.slf4j.LoggerFactory
import java.sql.DriverManager
import java.sql.SQLException
import java.util.*
import java.util.concurrent.TimeUnit

class EventHandlerImpl(val url: String,
                       val props: Properties,
                       val eventManager: EventManager,
                       val eventReader: EventReader
): EventHandler {
    private var stop = false

    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun start() {
        logger.info("start handling")

        PGProperty.ASSUME_MIN_SERVER_VERSION.set(props, "9.4")
        PGProperty.REPLICATION.set(props, "database")
        PGProperty.PREFER_QUERY_MODE.set(props, "simple")

        val connection = DriverManager.getConnection(url, props)

        logger.info("connected to {} as {}", url, PGProperty.USER.get(props))

        val replConnection = connection.unwrap(PGConnection::class.java)

        try {
            replConnection.replicationAPI
                .dropReplicationSlot("server_logical_slot")
            replConnection.replicationAPI
                .createReplicationSlot()
                .logical()
                .withSlotName("server_logical_slot")
                .withOutputPlugin("test_decoding")
                .make()
        } catch (e: SQLException) {
            logger.warn(e.message)
        }

        val stream = replConnection.replicationAPI
            .replicationStream()
            .logical()
            .withSlotName("server_logical_slot")
            .withSlotOption("include-xids", false)
            .withSlotOption("skip-empty-xacts", true)
            .withStatusInterval(20, TimeUnit.SECONDS)
            .start()

        logger.info("replication stream started")

        while(!stop){
            val msg = stream.readPending()

            if(msg == null){
                TimeUnit.MILLISECONDS.sleep(20)
                continue
            }

            logger.info("received msg from {}: {}", url, msg)

            val offset = msg.arrayOffset()
            val source = msg.array()
            val length = source.size - offset

            val s = String(source, offset, length)
            logger.debug("Reading: {}", s)

            try {
                val event = eventReader.read(s)
                eventManager.process(event)
            } catch (e: Exception){
                logger.error(e.message)
                throw e
            }
        }
    }

    override fun stop() {
        stop = true
    }

}