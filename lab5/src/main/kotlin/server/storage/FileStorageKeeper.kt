package server.storage

import server.data.IdGenerator
import share.data.movie.Movie
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import java.io.FileInputStream
import java.io.FileOutputStream
import kotlin.reflect.KProperty

class FileStorageKeeper(
    private val storage: Storage,
    private val idGenerator: IdGenerator
) : StorageKeeper {
    private lateinit var currentFilename: String

    override operator fun getValue(obj: Any, property: KProperty<*>): Storage {
        return storage
    }

    override fun openAndParse(filename: String){
        val fileReader = FileInputStream(filename).reader()

        var text: String
        fileReader.use {
            text = fileReader.readText()
        }

        currentFilename = filename


        val list = if(text.isBlank()){
            listOf()
        } else{
            Json.decodeFromString(ListSerializer(Movie.serializer()), text)
        }

        idGenerator.last = list.maxOfOrNull { it.id } ?: 0

        storage.setData(list)
    }

    override fun save(){
        val fileWriter = FileOutputStream(currentFilename).writer()

        val string = Json.encodeToString(ListSerializer(Movie.serializer()), storage.getAll())

        fileWriter.use { fileWriter.write(string) }
    }
}