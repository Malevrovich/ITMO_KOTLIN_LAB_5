package server.storage.storage_keeper

import server.storage.main.MainStorage
import kotlin.reflect.KProperty


interface StorageKeeper {
    operator fun getValue(obj: Any, property: KProperty<*>): MainStorage

    fun open()
    fun save()
}