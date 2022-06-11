package share.data.user

import data.checkNull
import java.util.*

class UserBuilderImpl: UserBuilder {
    var login: String? = null
    var password: ByteArray? = null

    override fun setLogin(login: String?): UserBuilder {
        if(login == null) return this

        if(login.isBlank()) {
            throw IllegalArgumentException("Поле login не может быть пустым")
        }

        this.login = login

        return this
    }

    override fun setPassword(password: ByteArray?): UserBuilder {
        if(password == null) return this

        if(password.isEmpty()) {
            throw IllegalArgumentException("Поле password не может быть пустым")
        }

        this.password = password

        return this
    }

    override fun setProps(props: Properties): UserBuilder {
        setLogin(getOrNull(props, "login") as String?)
        setPassword(getOrNull(props, "password") as ByteArray?)

        return this
    }

    override fun setDefault(): UserBuilder {
        login = "admin"
        password = "nimda".toByteArray()
        return this
    }

    override fun copyFrom(el: User?): UserBuilder {
        if(el == null) return this

        setLogin(el.login)
        setPassword(el.password)

        return this
    }

    override fun clear(): UserBuilder {
        login = null
        password = null
        return this
    }

    override fun build(): User {
        checkNull(login, "login")
        checkNull(password, "password")

        return User(login!!, password!!)
    }

    override fun buildDefault(): User {
        setDefault()
        return build()
    }
}