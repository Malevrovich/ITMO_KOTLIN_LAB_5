package share.data.user

import share.data.Builder
import java.util.*

interface UserBuilder: Builder<User> {

    fun setLogin(login: String?) : UserBuilder
    fun setPassword(password: ByteArray?): UserBuilder

    override fun setProps(props: Properties) : UserBuilder
    override fun setDefault() : UserBuilder

    override fun copyFrom(el: User?) : UserBuilder

    override fun clear() : UserBuilder
}