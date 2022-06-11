package server.database.repository

interface UserManager {
    fun check(login: String, password: ByteArray)

    fun register(login: String, password: ByteArray)
}