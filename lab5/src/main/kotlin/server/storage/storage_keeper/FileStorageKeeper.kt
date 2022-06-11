package server.storage.storage_keeper

import share.data.movie.Movie
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import org.slf4j.LoggerFactory
import server.storage.main.MainStorage
import java.io.FileInputStream
import java.io.FileOutputStream
import kotlin.reflect.KProperty

class FileStorageKeeper(
    private val path: String,
    private val storage: MainStorage,
) : StorageKeeper {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    override operator fun getValue(obj: Any, property: KProperty<*>): MainStorage {
        return storage
    }

    override fun open(){
        val fileReader = FileInputStream(path).reader()

        var text: String
        fileReader.use {
            text = fileReader.readText()
        }

        val list = if(text.isBlank()){
            listOf()
        } else{
            Json.decodeFromString(ListSerializer(Movie.serializer()), text)
        }

        storage.getData().setData(list)
    }

    override fun save(){
        logger.info("Saving collection at {}", path)
        val fileWriter = FileOutputStream(path).writer()

        val string = Json.encodeToString(ListSerializer(Movie.serializer()), storage.getAll())

        fileWriter.use { fileWriter.write(string) }
    }
}