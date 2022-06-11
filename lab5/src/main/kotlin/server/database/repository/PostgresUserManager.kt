package server.database.repository

import org.slf4j.LoggerFactory
import share.util.RegisterException
import java.security.MessageDigest
import java.sql.SQLException
import javax.security.auth.login.LoginException

class PostgresUserManager(val repository: Repository): UserManager {

    val logger = LoggerFactory.getLogger(this.javaClass)

    val messageDigest = MessageDigest.getInstance("SHA-512")

    private val userCheckSQL = "SELECT * FROM users WHERE login = ?"
    override fun check(login: String, password: ByteArray) {
        val stmt = repository.prepareStatement(userCheckSQL)

        stmt.setString(1, login)

        logger.info("Executing userCheckSQL")
        try {
            val res = stmt.executeQuery()

            if(!res.next()){
                logger.info("Incorrect login")
                throw LoginException("Неверный логин!")
            }

            val salt = res.getString("salt")
            val hash = messageDigest.digest((String(password, Charsets.UTF_8) + salt).toByteArray())

            logger.debug("Decoded password+salt: {}", String(password, Charsets.UTF_8) + salt)
            logger.debug("Hash: {}", hash)

            if((res.getString("login") != login)
                or (!res.getBytes("password").contentEquals(hash))){
                logger.info("Incorrect password")

                repository.closeConnection(stmt)
                throw LoginException("Неверный логин или пароль!")
            }
        } catch (e: SQLException) {
            logger.error(e.message)

            repository.closeConnection(stmt)
            throw e
        }
    }

    private val userRegisterSQL = "INSERT INTO users (login, password, salt) VALUES(?, ?, ?);"
    override fun register(login: String, password: ByteArray) {
        val stmt = repository.prepareStatement(userRegisterSQL)

        val salt = List(5) {
            (('a'..'z') + ('A'..'Z') + ('0'..'9')).random()
        }.joinToString("")

        val hash = messageDigest.digest((String(password, Charsets.UTF_8) + salt).toByteArray())

        stmt.setString(1, login)
        stmt.setBytes(2, hash)
        stmt.setString(3, salt)

        try {
            if(stmt.executeUpdate() != 1) {
                repository.closeConnection(stmt)
                throw RegisterException("Пользователь не был зарегистрирован, выберите другой логин")
            }
        } catch (e: SQLException) {
            logger.error(e.message)

            repository.closeConnection(stmt)
            throw e
        }
        repository.closeConnection(stmt)
    }

}