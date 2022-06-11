package server.commands.cmds

import server.storage.main.MainStorage
import share.commands.dto.CommandType
import share.data.user.User

abstract class StorageCmd(open val storage: MainStorage, override val name: String,
                          open val user: User, override val type: CommandType
): Command(name, type)