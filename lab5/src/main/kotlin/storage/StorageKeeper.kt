package storage

import kotlin.reflect.KProperty


interface StorageKeeper {
    operator fun getValue(obj: Any, property: KProperty<*>): Storage

    fun open(filename: String)
    fun save()
}