package share.user_io.user_reader

interface UserReader {
    fun nextInt(): Int
    fun hasNextInt(): Boolean

    fun nextFloat(): Float
    fun hasNextFloat(): Boolean

    fun nextLine(): String
    fun hasNextLine(): Boolean
}