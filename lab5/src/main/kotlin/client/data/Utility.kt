package data

import share.io.input.Input
import share.io.output.Output
import share.localization.Localization

fun checkNull(obj: Any?, name: String){
    if(obj == null){
        throw IllegalArgumentException("Поле $name должно быть инициализировано")
    }
}

inline fun <reified T: Enum<T>> askEnum(inp: Input, out: Output, field: String,
                                                            localization: Localization, withNull: Boolean = false): T?{

    while (true){
        out.print(localization.getString("possibleValues") + field + ":")
        out.println(enumValues<T>().joinToString { it.name })

        while(true) {
            out.print( localization.getString("askValue") + field + ":")
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

fun intFormatErrorMsg(field: String, localization: Localization)
                        = field + localization.getString("intFormatError")
fun floatFormatError(field: String, localization: Localization)
                        = field + localization.getString("floatFormatError")