package share

import client.data.movie.MovieReader
import client.data.movie.MovieReaderImpl
import client.data.person.PersonReader
import client.data.person.PersonReaderImpl
import org.kodein.di.*
import share.commands.util.CommandReader
import share.commands.util.CommandReaderImpl
import share.data.coordinates.CoordinatesBuilder
import share.data.coordinates.CoordinatesBuilderImpl
import share.data.movie.MovieBuilder
import share.data.movie.MovieBuilderImpl
import share.data.person.PersonBuilder
import share.data.person.PersonBuilderImpl
import share.data.user.UserBuilder
import share.data.user.UserBuilderImpl
import share.io.input.Input
import share.io.input.StreamInput
import share.io.output.Output
import share.io.output.StreamOutput


val localModule = DI.Module(name = "local"){
    bind<Input>(tag="local") { singleton { StreamInput(System.`in`) }}
    bind<Output>(tag="local") { singleton { StreamOutput(System.out) }}

    bind<CommandReader>(tag="local") { singleton(sync=false) { CommandReaderImpl(instance(), instance()) } }

    bindSingleton<CoordinatesBuilder>(sync=false) { CoordinatesBuilderImpl() }
    bindSingleton<PersonBuilder>(sync=false) { PersonBuilderImpl() }
    bindSingleton<MovieBuilder>(sync=false) { MovieBuilderImpl(instance(), instance(), instance()) }
    bindSingleton<UserBuilder>(sync=false) { UserBuilderImpl() }

//    bindSingleton<CoordinatesReader>(sync=false) { CoordinatesReaderImpl(instance()) }
    bindSingleton<PersonReader>(sync=false) { PersonReaderImpl(instance()) }
    bindSingleton<MovieReader>(sync=false) { MovieReaderImpl(instance(), instance(), instance(), instance()) }
}