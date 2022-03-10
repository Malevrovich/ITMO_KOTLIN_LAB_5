package data

import util.ParseException
import java.io.PrintStream
import java.util.*


fun checkFloat(s: Scanner, field: String){
    if(!s.hasNextFloat()){
        throw ParseException("$field должно быть числом")
    }
}

fun checkInt(s: Scanner, field: String){
    if(!s.hasNextInt()){
        s.nextLine()
        throw ParseException("Поле $field должно быть целым числом")
    }
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