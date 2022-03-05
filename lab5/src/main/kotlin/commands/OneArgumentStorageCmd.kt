package commands

import data.movie.Movie
import storage.Storage

abstract class OneArgumentStorageCmd(open val movie: Movie,
                                     storage: Storage,
                                     override val name: String): StorageCmd(storage, name)