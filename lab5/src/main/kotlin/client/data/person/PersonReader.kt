package client.data.person

import share.data.person.Person
import share.data.person.PersonBuilder
import share.io.input.Input
import share.io.output.Output

interface PersonReader {
    fun askPerson(inp: Input, out: Output): Person

    fun askName(inp: Input, out: Output, personBuilder: PersonBuilder)

    fun askBirthday(inp: Input, out: Output, personBuilder: PersonBuilder)
}