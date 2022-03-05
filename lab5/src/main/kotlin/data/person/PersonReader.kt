package data.person

import java.io.InputStream
import java.io.OutputStream
import java.io.PrintStream
import java.util.*

interface PersonReader {
    fun askPerson(inp: Scanner, out: PrintStream): Person

    fun askName(inp: Scanner, out: PrintStream, personBuilder: PersonBuilder)

    fun askBirthday(inp: Scanner, out: PrintStream, personBuilder: PersonBuilder)
}