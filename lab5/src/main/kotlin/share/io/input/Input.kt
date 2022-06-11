package share.io.input

interface Input {
    fun nextLine(): String

    fun read(): Char

    fun ready(): Boolean
}