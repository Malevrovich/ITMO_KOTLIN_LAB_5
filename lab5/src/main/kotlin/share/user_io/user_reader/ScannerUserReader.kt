package share.user_io.user_reader

import java.io.EOFException
import java.io.InputStream
import java.util.*

class ScannerUserReader(inputStream: InputStream): UserReader {
    private val scanner = Scanner(inputStream).useDelimiter("\n")

    override fun nextInt() = scanner.nextInt()
    override fun hasNextInt() = scanner.hasNextInt()

    override fun nextFloat() = scanner.nextFloat()
    override fun hasNextFloat() = scanner.hasNextFloat()

    override fun nextLine() = scanner.nextLine() ?: throw EOFException("Входной поток неожиданно закончился")
    override fun hasNextLine() = scanner.hasNextLine()
}