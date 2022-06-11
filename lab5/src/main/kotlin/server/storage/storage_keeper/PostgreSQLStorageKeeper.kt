package server.storage.storage_keeper

import org.slf4j.LoggerFactory
import server.database.event.EventHandler
import server.database.event.EventManager
import server.database.repository.Repository
import server.storage.main.MainStorage
import kotlin.reflect.KProperty

class PostgreSQLStorageKeeper(
    private val storage: MainStorage,
    private val eventHandler: EventHandler,
    private val eventManager: EventManager,
    private val repository: Repository
): StorageKeeper {

    val logger = LoggerFactory.getLogger(this.javaClass)

    override fun getValue(obj: Any, property: KProperty<*>): MainStorage {
        return storage
    }

    override fun open() {
        logger.info("Synchronizing with DB")
        eventManager.init()
        repository.synchronize(storage.getData())

        logger.info("Handling DB differences")
        eventHandler.start()
    }

    override fun save() {
        return
    }
}