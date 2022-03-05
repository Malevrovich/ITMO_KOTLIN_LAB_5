package commands

import storage.Storage

abstract class StorageCmd(open val storage: Storage, override val name: String): Command(name) {
}