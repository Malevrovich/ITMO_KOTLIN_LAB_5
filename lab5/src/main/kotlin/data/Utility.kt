package data

import java.io.PrintStream
import java.util.*


fun checkFloat(s: Scanner, out: PrintStream, field: String): Boolean{
    if(!s.hasNextFloat()){
        s.nextLine()
        out.println("Поле $field должно быть числом")
        return false
    }
    return true
}

fun checkInt(s: Scanner, out: PrintStream, field: String): Boolean{
    if(!s.hasNextInt()){
        s.nextLine()
        out.println("Поле $field должно быть целым числом")
        return false
    }
    return true
}

fun checkNull(obj: Any?, name: String){
    if(obj == null){
        throw IllegalArgumentException("Поле $name должно быть инициализировано")
    }
}

inline fun <reified T: Enum<T>> askEnum(inp: Scanner, out: PrintStream, field: String, withNull: Boolean = false): T?{

    while (true){
        out.print("Возможные значения $field: ")
        out.println(enumValues<T>().joinToString { it.name })

        while(true) {
            out.print("Введите значение $field: ")
            val s = inp.nextLine()

            if(s.isBlank() && withNull){
                return null
            }

            if (s.trim().split(" ").size != 1) {
                println("Поле $field содержит только одно значение")
                continue
            }

            if(s.trim() !in enumValues<T>().map{ it.name }){
                println("Поле $field должно являться одним из предложенных значений")
                continue
            }

            return enumValueOf<T>(s)
        }
    }
}