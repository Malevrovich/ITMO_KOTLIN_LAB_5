package client.auth

import share.io.input.Input
import share.io.output.Output

class AuthenticateReaderImpl(val loginManager: LoginManager): AuthenticateReader {
    override fun auth(input: Input, output: Output) {
        while(true) {
            output.println("Для входа введите - login, для регистрации - register:")

            val type = input.nextLine()

            if(type !in listOf("login", "register")) {
                continue
            }

            output.println("Введите логин:")

            val login = input.nextLine()

            output.println("Введите пароль:")

            val password = input.nextLine()

            try{
                when(type){
                    "login" -> loginManager.signIn(login, password)
                    "register" -> loginManager.signUp(login, password)
                }
            } catch (e: Exception) {
                output.println(e.message)
                continue
            }

            when(type) {
                "login" -> output.println("Вы успешно вошли!")
                "register" -> output.println("Учетная запись $login успешно создана!")
            }

            break
        }
    }
}