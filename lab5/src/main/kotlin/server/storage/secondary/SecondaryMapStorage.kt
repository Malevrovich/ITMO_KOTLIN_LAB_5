package server.storage.secondary

import java.util.*
import kotlin.collections.HashMap

class SecondaryMapStorage<T>: SecondaryStorage<T> {
    val data: MutableMap<Int, T> = Collections.synchronizedMap( HashMap() )

    override fun get(id: Int): T? {
        return data.getOrDefault(id, null)
    }

    override fun set(id: Int, el: T) {
        data[id] = el
    }

    override fun remove(id: Int) {
        data.remove(id)
    }

    override fun update(id: Int, el: T) {
        data.getOrElse(id){
            throw IllegalArgumentException("Нет элемента с id = $id")
        }
        data[id] = el
    }

    override fun clear() {
        data.clear()
    }

    override fun contains(id: Int): Boolean {
        return data.containsKey(id)
    }
}