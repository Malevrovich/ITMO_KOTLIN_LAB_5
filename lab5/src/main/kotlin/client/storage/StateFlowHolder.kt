package client.storage

import kotlinx.coroutines.flow.StateFlow
import share.data.movie.Movie

interface StateFlowHolder {
    fun getState(): StateFlow<List<Movie>>

    suspend fun startListen()

    fun setOrder(type: ItemField, ascending: Boolean)
    fun setFilter(type: ItemField, value: String)
    
    fun stop()
}