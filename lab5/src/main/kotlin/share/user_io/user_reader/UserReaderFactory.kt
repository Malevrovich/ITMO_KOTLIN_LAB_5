package share.user_io.user_reader

import java.io.InputStream

interface UserReaderFactory{
    fun buildUserReader(inputStream: InputStream): UserReader
}