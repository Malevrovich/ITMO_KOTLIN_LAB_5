package server.database.repository

import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import org.slf4j.LoggerFactory
import server.storage.data.Data
import share.data.coordinates.Coordinates
import share.data.coordinates.CoordinatesBuilder
import share.data.movie.Movie
import share.data.movie.MovieBuilder
import share.data.movie.MovieGenre
import share.data.person.Country
import share.data.person.Person
import share.data.person.PersonBuilder
import share.data.user.User
import share.data.user.UserBuilder
import share.util.NoPermissionsException
import java.sql.*
import javax.sql.ConnectionPoolDataSource

class PostgresRepository (
    val connectionPool: ConnectionPoolDataSource,
    val movieBuilder: MovieBuilder,
    val coordinatesBuilder: CoordinatesBuilder,
    val personBuilder: PersonBuilder,
    val userBuilder: UserBuilder
): Repository {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    val checkAccessSQL = "SELECT * FROM movie INNER JOIN users USING(uid) WHERE login=? and movie_id=?"
    fun checkAccess(id: Int, user: User){
        val conn = connectionPool.pooledConnection.connection

        val stmt = conn.prepareStatement(checkAccessSQL)
        stmt.setString(1, user.login)
        stmt.setInt(2, id)

        val res = stmt.executeQuery()
        if(!res.next()) throw NoPermissionsException("У вас недостаточно прав для редактирования обьекта с id = $id")
    }

    private fun extractMovie(resultSet: ResultSet): Movie{
        movieBuilder.setId(resultSet.getInt("movie_id"))

        movieBuilder.setName(resultSet.getString("name"))
        movieBuilder.setGenre(MovieGenre.valueOf(resultSet.getString("genre")))
        movieBuilder.setLength(resultSet.getInt("length"))

        movieBuilder.setCreationDate(
            resultSet.getTimestamp("creation_date")
                .toLocalDateTime().toKotlinLocalDateTime()
        )

        movieBuilder.setOscarsCount(resultSet.getInt("oscars_count"))
        movieBuilder.setUsaBoxOffice(resultSet.getFloat("usa_box_office"))

        coordinatesBuilder.setX(resultSet.getFloat("x"))
        coordinatesBuilder.setY(resultSet.getInt("y"))

        movieBuilder.setCoordinates(coordinatesBuilder.build())

        val nationality = resultSet.getString("nationality")
        if(nationality != null) {
            personBuilder.setNationality(Country.valueOf(nationality))
        }

        personBuilder.setBirthday(
            resultSet.getTimestamp("birthday")?.toLocalDateTime()?.toKotlinLocalDateTime()
        )

        personBuilder.setName(resultSet.getString(11))

        movieBuilder.setScreenwriter(personBuilder.build())

        userBuilder.setLogin(resultSet.getString("login"))
        userBuilder.setPassword(resultSet.getBytes("password"))

        movieBuilder.setUser(userBuilder.build())

        return movieBuilder.build()
    }

    private val getAllSQL =
        """SELECT * FROM movie
                |INNER JOIN coordinates
                |USING(movie_id)
                |INNER JOIN persons 
                |USING(movie_id)
                |INNER JOIN users
                |USING(uid);""".trimMargin()

    override fun getAll(): List<Movie> {
        val stmt = connectionPool.pooledConnection.connection.prepareStatement(getAllSQL)

        logger.info("Executing getAll query")
        try {
            val resultSet = stmt.executeQuery()

            val res = mutableListOf<Movie>()
            while (resultSet.next()) {
                res.add(extractMovie(resultSet))
            }

            stmt.connection.close()
            return res
        } catch (e: SQLException){
            logger.error(e.message)
            throw e
        }
    }

    private val getByIdSQL =
        """SELECT * FROM movie
                |INNER JOIN coordinates 
                |USING(movie_id)
                |INNER JOIN persons
                |USING(movie_id)
                |INNER JOIN users
                |USING(uid)
                |WHERE movie.id = ?;
                """.trimMargin()

    override fun getBy(id: Int): Movie {
        val stmt = connectionPool.pooledConnection.connection.prepareStatement(getByIdSQL)

        stmt.setInt(1, id)

        logger.info("Executing getBy query")
        try {
            val resultSet = stmt.executeQuery()

            val res = extractMovie(resultSet)

            stmt.connection.close()
            return res
        } catch (e: SQLException){
            logger.error(e.message)
            throw e
        }
    }

    private val removeBySQL =
        """WITH ids AS (
                    | DELETE FROM movie 
                    | WHERE movie_id = ? 
                    | RETURNING movie_id
                    |), delete_coordinates AS (
                    | DELETE FROM coordinates
                    | WHERE movie_id IN (SELECT movie_id FROM ids)
                    |)
                    |DELETE FROM persons WHERE movie_id IN (SELECT movie_id FROM ids)
                """.trimMargin()

    override fun removeBy(id: Int, user: User): Int {
        checkAccess(id, user)

        val stmt = connectionPool.pooledConnection.connection.prepareStatement(removeBySQL)
        stmt.setInt(1, id)

        logger.info("Executing removeBy query")
        try {
            val res = stmt.executeUpdate()
            stmt.connection.close()

            return res
        } catch (e: SQLException){
            logger.error(e.message)
            throw e
        }
    }

    private val updateMovieSQL =
        """UPDATE movie
                    |SET name = ?,
                    |    creation_date = ?,
                    |    oscars_count = ?,
                    |    usa_box_office = ?,
                    |    length = ?,
                    |    genre = ?
                    |WHERE movie_id=?;
                """.trimMargin()

    private fun updateMovie(id: Int, movie: Movie, conn: Connection) {
        val stmt = conn.prepareStatement(updateMovieSQL)

        with(movie) {
            stmt.setString(1, name)
            stmt.setObject(2, creationDate.toJavaLocalDateTime())
            stmt.setInt(3, oscarsCount)
            stmt.setFloat(4, usaBoxOffice)
            stmt.setInt(5, length)
            stmt.setObject(6, genre.name, Types.OTHER)
            stmt.setInt(7, id)
        }

        logger.info("Executing updateMovie query")
        try {
            stmt.executeUpdate()
        } catch (e: SQLException){
            logger.error(e.message)
            throw e
        }
    }

    private val updateCoordinatesSQL =
        """UPDATE coordinates
                    |SET x = ?,
                    |    y = ?,
                    |    movie_id = ?
                    |    WHERE movie_id = ?;
                    """.trimMargin()

    private fun updateCoordinates(id: Int, coords: Coordinates, conn: Connection){
        val stmt = conn.prepareStatement(updateCoordinatesSQL)

        with(coords){
            stmt.setFloat(1, x)
            stmt.setInt(2, y)
            stmt.setInt(3, id)
            stmt.setInt(4, id)
        }

        logger.info("Executing updateCoordinates query")
        try {
            stmt.executeUpdate()
        }catch (e: SQLException){
            logger.error(e.message)
            throw e
        }
    }

    private val updatePersonSQL =
        """UPDATE persons
                |SET name = ?,
                |    birthday = ?,
                |    nationality = ?,
                |    movie_id = ?
                |    WHERE movie_id = ?;
                """.trimMargin()

    private fun updatePerson(id: Int, person: Person, conn: Connection){
        val stmt = conn.prepareStatement(updatePersonSQL)

        with(person){
            stmt.setString(1, name)
            stmt.setObject(2, birthday?.toJavaLocalDateTime())
            stmt.setObject(3, nationality?.name, Types.OTHER)
            stmt.setInt(4, id)
            stmt.setInt(5, id)
        }

        logger.info("Executing updatePersons query")
        try {
            stmt.executeUpdate()
        } catch (e: SQLException){
            logger.error(e.message)
            throw e
        }
    }


    override fun update(id: Int, movie: Movie, user: User) {
        val conn = connectionPool.pooledConnection.connection

        checkAccess(id, user)

        updateMovie(id, movie, conn)
        updateCoordinates(id, movie.coordinates, conn)
        updatePerson(id, movie.screenwriter, conn)

        conn.close()
    }

    private val insertSQL =
        """WITH ids AS (
                        INSERT INTO movie
                        (name, oscars_count, usa_box_office, length, genre, uid)
                        VALUES (?, ?, ?, ?, ?, (SELECT uid FROM users WHERE login=?))
                        RETURNING movie_id
                   ), insert_coordinates AS (
                        INSERT INTO coordinates
                        (movie_id, x, y)
                        VALUES ((SELECT movie_id FROM ids), ?, ?)
                   )
                   INSERT INTO persons
                   (movie_id, name, birthday, nationality)
                   VALUES ((SELECT movie_id FROM ids), ?, ?, ?);  
                """.trimIndent()

    override fun insert(movie: Movie): Boolean {
        val conn = connectionPool.pooledConnection.connection

        val stmt = conn.prepareStatement(insertSQL)


        stmt.setString(1, movie.name)
        stmt.setInt(2, movie.oscarsCount)
        stmt.setFloat(3, movie.usaBoxOffice)
        stmt.setInt(4, movie.length)
        stmt.setObject(5, movie.genre.name, Types.OTHER)
        stmt.setString(6, movie.user.login)

        stmt.setFloat(7, movie.coordinates.x)
        stmt.setInt(8, movie.coordinates.y)

        stmt.setString(9, movie.screenwriter.name)
        stmt.setObject(10, movie.screenwriter.birthday?.toJavaLocalDateTime())
        stmt.setObject(11, movie.screenwriter.nationality?.name, Types.OTHER)

        logger.info("Executing insert query")
        try {
            stmt.executeUpdate()
        } catch (e: SQLException){
            logger.error(e.message)
            throw e
        }

        conn.close()
        return true
    }

    override fun prepareStatement(req: String): PreparedStatement {
        return connectionPool.pooledConnection.connection.prepareStatement(req)
    }

    override fun closeConnection(statement: PreparedStatement){
        statement.connection.close()
    }

    override fun synchronize(data: Data) {
        data.setData(getAll())
    }
}