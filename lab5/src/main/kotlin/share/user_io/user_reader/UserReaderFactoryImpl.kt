package share.user_io.user_reader

import java.io.InputStream

class UserReaderFactoryImpl : UserReaderFactory {
    override fun buildUserReader(inputStream: InputStream): UserReader {
        return ScannerUserReader(inputStream)
    }
}