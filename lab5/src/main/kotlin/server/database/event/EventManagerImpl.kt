package server.database.event

import org.slf4j.LoggerFactory
import server.storage.data.Data
import server.storage.secondary.SecondaryStorage
import share.data.coordinates.Coordinates
import share.data.coordinates.CoordinatesBuilder
import share.data.movie.Movie
import share.data.movie.MovieBuilder
import share.data.person.Person
import share.data.person.PersonBuilder
import share.data.user.User
import java.io.FileInputStream
import java.sql.DriverManager
import java.util.*

class EventManagerImpl(val source: Data,
                       val movieBuilder: MovieBuilder,
                       val coordinatesBuilder: CoordinatesBuilder,
                       val personBuilder: PersonBuilder,
                       val personStorage: SecondaryStorage<Person>,
                       val coordinatesStorage: SecondaryStorage<Coordinates>,
                       val movieStorage: SecondaryStorage<Movie>
): EventManager {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    private val uidToLogin = mutableMapOf<Int, String>()
    override fun init() {
        val props = Properties()
        props.load(FileInputStream("D:\\Programming\\ITMO_KOTLIN\\lab5\\src\\main\\resources\\cfg\\db.cfg"))

        val conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/mydb", props)

        val stmt = conn.prepareStatement("SELECT uid, login FROM users")
        val res = stmt.executeQuery()

        while(res.next()){
            uidToLogin[res.getInt("uid")] = res.getString("login")
        }
    }

    private fun eventToBuilder(event: Event) {
        event.props["id"] = event.id

        val builder = when(event.table) {
            "coordinates" -> coordinatesBuilder
            "persons" -> personBuilder
            "movie" -> movieBuilder
            "users" -> return
            else -> throw IllegalArgumentException("Unknown table ${event.table}")
        }


        if((event.table == "movie") and (event.props.keys.contains("uid"))) {
//            logger.debug("Event table {}, uid {}", event.table, event.props.getProperty("uid"))
            movieBuilder.setUser(User(uidToLogin[event.props.getProperty("uid").toInt()]!!, "hah".toByteArray()))
        }

        builder.setProps(event.props)
    }

    private fun insertFull(id: Int){
        movieBuilder.copyFrom(movieStorage.get(id))
        movieBuilder.setScreenwriter(personStorage.get(id))
        movieBuilder.setCoordinates(coordinatesStorage.get(id))

        movieBuilder.setId(id)

        val movie = movieBuilder.build()
        logger.debug("Insertion: $movie")

        movieStorage.remove(id)
        coordinatesStorage.remove(id)
        coordinatesStorage.remove(id)

        source.add(movie)
    }

    override fun process(event: Event) {
        when(event.type){
            EventType.BEGIN -> {
                logger.info("BEGIN event processed")
                return
            }
            EventType.COMMIT -> {
                logger.info("COMMIT event processed")
                return
            }
            EventType.UPDATE -> {
                if(event.table == "users") return
                logger.info("Processing UPDATE event")

                movieBuilder.copyFrom(source.get(event.id))

                when(event.table){
                    "coordinates" -> coordinatesBuilder
                        .copyFrom(source.get(event.id)?.coordinates
                                                ?: coordinatesStorage.get(event.id))
                    "persons" -> personBuilder
                        .copyFrom(source.get(event.id)?.screenwriter
                                                ?: personStorage.get(event.id))
                }

                eventToBuilder(event)

                when(event.table){
                    "coordinates" -> movieBuilder.setCoordinates(coordinatesBuilder.build())
                    "persons" -> movieBuilder.setScreenwriter(personBuilder.build())
                }

                source.update(event.id, movieBuilder.build())
            }
            EventType.INSERT -> {
                logger.info("Processing INSERT event, table = {}", event.table)

                eventToBuilder(event)

                when(event.table){
                    "coordinates" -> {
                        coordinatesStorage.set(event.id, coordinatesBuilder.build())
                        if(movieStorage.contains(event.id) and personStorage.contains(event.id)){
                            insertFull(event.id)
                        }
                    }
                    "persons" -> {
                        personStorage.set(event.id, personBuilder.build())
                        if(movieStorage.contains(event.id) and coordinatesStorage.contains(event.id)){
                            insertFull(event.id)
                        }
                    }
                    "movie" -> {
                        movieBuilder.setCoordinates(coordinatesBuilder.buildDefault())
                        movieBuilder.setScreenwriter(personBuilder.buildDefault())

                        movieStorage.set(event.id, movieBuilder.build())

                        if(coordinatesStorage.contains(event.id) and personStorage.contains(event.id)){
                            insertFull(event.id)
                        }
                    }
                    "users" -> {
                        uidToLogin[event.id] = event.props.getProperty("login")
                    }
                    else -> throw IllegalArgumentException("Unknown name of table ${event.table}")
                }
                logger.info("Element added")
            }
            EventType.DELETE -> {
                logger.info("Processing DELETE event")

                if(event.table == "movie") {
                    source.remove(event.id)
                }
            }
            EventType.TRUNCATE -> {
                logger.info("Processing TRUNCATE event")

                when(event.table) {
                    "movie" -> source.getSet().clear()
                    "coordinates" -> coordinatesStorage.clear()
                    "persons" -> personStorage.clear()
                }
            }
        }
    }
}