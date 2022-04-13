package client.data.person

import share.data.person.Person
import share.user_io.user_reader.UserReader
import share.user_io.user_writer.UserWriter

interface PersonReader {
    fun askPerson(inp: UserReader, out: UserWriter): Person

    fun askName(inp: UserReader, out: UserWriter, personBuilder: PersonBuilder)

    fun askBirthday(inp: UserReader, out: UserWriter, personBuilder: PersonBuilder)
}