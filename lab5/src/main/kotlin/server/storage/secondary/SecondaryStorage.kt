package server.storage.secondary

interface SecondaryStorage<T> {
    fun get(id: Int): T?

    fun contains(id: Int): Boolean

    fun set(id: Int, el: T)

    fun remove(id: Int)

    fun update(id: Int, el: T)

    fun clear()
}