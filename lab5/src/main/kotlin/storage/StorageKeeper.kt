package storage

import kotlin.reflect.KProperty


interface StorageKeeper {
    operator fun getValue(obj: Any, property: KProperty<*>): Storage

    fun openAndParse(filename: String)
    fun save()
}