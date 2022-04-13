package share.commands

import server.storage.Storage
import share.commands.util.CommandType

abstract class StorageCmd(open val storage: Storage, override val name: String,
                            override val type: CommandType): Command(name, type)