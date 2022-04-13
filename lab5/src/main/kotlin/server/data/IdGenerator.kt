package server.data

class IdGenerator {
    var last: Int = 0

    fun generate(): Int{
        return ++last;
    }
}