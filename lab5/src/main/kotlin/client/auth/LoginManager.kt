package client.auth

import share.data.user.User

interface LoginManager {
    fun signIn(login: String, password: String)

    fun signUp(login: String, password: String)

    fun disconnect()

    fun getCurrentSession(): User
}