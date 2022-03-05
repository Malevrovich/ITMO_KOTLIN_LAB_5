package storage

import data.IdGenerator
import data.movie.Movie
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import org.kodein.di.DI
import org.kodein.di.instance
import java.io.FileInputStream
import java.io.FileOutputStream
import kotlin.reflect.KProperty

class FileStorageKeeper(di: DI) : StorageKeeper {
    private val currentStorage: Storage by di.instance()
    private lateinit var currentFilename: String
    private val idGenerator: IdGenerator by di.instance()

    override operator fun getValue(obj: Any, property: KProperty<*>): Storage {
        return currentStorage
    }

    override fun open(filename: String){
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

        idGenerator.last = list.maxOf { it.id }

        currentStorage.setData(list)
    }

    override fun save(){
        val fileWriter = FileOutputStream(currentFilename).writer()

        val string = Json.encodeToString(ListSerializer(Movie.serializer()), currentStorage.getAll())

        fileWriter.use { fileWriter.write(string) }
    }
}