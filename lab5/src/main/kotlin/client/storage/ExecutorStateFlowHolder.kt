package client.storage

import client.commands.dto.CommandDTOFactory
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withTimeout
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import share.data.movie.Movie
import share.executor.Executor

class ExecutorStateFlowHolder(
    private val commandsDTOFactory: CommandDTOFactory,
    private val executor: Executor
): StateFlowHolder {

    private val state = MutableStateFlow<List<Movie>>(listOf())
    private var stop: Boolean = false

    private var filterType = ItemField.ID
    private var filterValue = ""
    private var order = ItemField.ID
    private var ascending = true

    override fun getState(): StateFlow<List<Movie>> = state

    private fun List<Movie>.filterBy(filterType: ItemField, filterValue: String): List<Movie>{
        return when(filterType){
            ItemField.ID -> this.filter { it.id.toString().contains(filterValue) }
            ItemField.NAME -> this.filter { it.name.contains(filterValue) }
            ItemField.COUNTRY -> this.filter { it.screenwriter.nationality?.name.toString().contains(filterValue) }
            ItemField.GENRE -> this.filter { it.genre.name.contains(filterValue) }
            ItemField.LENGTH -> this.filter { it.length.toString().contains(filterValue) }
            ItemField.OSCARS_COUNT -> this.filter { it.oscarsCount.toString().contains(filterValue) }
            ItemField.SCREENWRITER -> this.filter { it.screenwriter.name.contains(filterValue) }
            ItemField.USA_BOX_OFFICE -> this.filter { it.usaBoxOffice.toString().contains(filterValue) }
            ItemField.X -> this.filter { it.coordinates.x.toString().contains(filterValue) }
            ItemField.Y -> this.filter { it.coordinates.y.toString().contains(filterValue) }
        }
    }

    private fun List<Movie>.orderBy(field: ItemField, ascending: Boolean = true): List<Movie>{
        return when(field){
            ItemField.COUNTRY -> if(ascending) this.sortedBy { it.screenwriter.nationality?.name }
                else this.sortedByDescending { it.screenwriter.nationality?.name }

            ItemField.ID -> if(ascending) this.sortedBy { it.id }
                else this.sortedByDescending { it.id }

            ItemField.NAME -> if(ascending) this.sortedBy { it.name }
                else this.sortedByDescending { it.name }

            ItemField.GENRE -> if(ascending) this.sortedBy { it.genre.name }
                else this.sortedByDescending { it.genre.name }

            ItemField.LENGTH -> if(ascending) this.sortedBy { it.length }
                else this.sortedByDescending { it.length }

            ItemField.USA_BOX_OFFICE -> if(ascending) this.sortedBy { it.usaBoxOffice }
                else this.sortedByDescending { it.usaBoxOffice }

            ItemField.SCREENWRITER -> if(ascending) this.sortedBy { it.screenwriter.name }
                else this.sortedByDescending { it.screenwriter.name }

            ItemField.OSCARS_COUNT -> if(ascending) this.sortedBy { it.oscarsCount }
                else this.sortedByDescending { it.oscarsCount }

            ItemField.X -> if(ascending) this.sortedBy { it.coordinates.x }
                else this.sortedByDescending { it.coordinates.x }

            ItemField.Y -> if(ascending) this.sortedBy { it.coordinates.y }
                else this.sortedByDescending { it.coordinates.y }
        }
    }

    override suspend fun startListen() = coroutineScope{
        while(!stop and isActive){
            val cmd = commandsDTOFactory.buildGetAll()
            withTimeout(60 * 1000) {
                val res: List<Movie> = Json.decodeFromString(executor.execute(cmd).out)
                state.value = res.filterBy(filterType, filterValue).orderBy(order, ascending)
            }
            delay(200)
        }
    }

    override fun setFilter(type: ItemField, value: String) {
        filterType = type
        filterValue = value
    }

    override fun setOrder(type: ItemField, ascending: Boolean) {
        order = type
        this.ascending = ascending
    }

    override fun stop() {
        stop = true
    }
}