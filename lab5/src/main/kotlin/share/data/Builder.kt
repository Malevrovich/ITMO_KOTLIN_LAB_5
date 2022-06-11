package share.data

import java.util.*

interface Builder<T> {

    fun getOrNull(props: Properties, key: String): Any?{
        if(key !in props.keys){
            return null
        }
        return props[key]
    }

    fun build() : T

    fun buildDefault() : T

    fun setProps(props: Properties) : Builder<T>
    fun setDefault() : Builder<T>

    fun copyFrom(el: T?) : Builder<T>

    fun clear() : Builder<T>
}