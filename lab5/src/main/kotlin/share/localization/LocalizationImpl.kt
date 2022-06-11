package share.localization

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import java.io.FileInputStream
import java.util.*

class LocalizationImpl: Localization {
    private var curLanguage = Language.RUSSIAN
    private var props = Properties()

    private var states: MutableMap<String, MutableState<String>> = mutableMapOf()

    init {
        props.load(
            FileInputStream("D:\\Programming\\ITMO_KOTLIN\\lab5\\src\\main\\resources\\strings\\russian.cfg")
                .reader(Charsets.UTF_8)
        )
    }

    override fun setLanguage(language: Language) {
        curLanguage = language
        when(language){
            Language.RUSSIAN -> props.load(
                FileInputStream("D:\\Programming\\ITMO_KOTLIN\\lab5\\src\\main\\resources\\strings\\russian.cfg")
                    .reader(Charsets.UTF_8)
            )
            Language.ENGLISH -> props.load(FileInputStream("D:\\Programming\\ITMO_KOTLIN\\lab5\\src\\main\\resources\\strings\\english.cfg"))
            else -> throw IllegalArgumentException("Language ${language.name} not supported")
        }
        for((k, _) in states.entries){
            states[k]!!.value = props.getProperty(k).trim('"')
        }
    }

    override fun getState(name: String): State<String> {
        if(states.containsKey(name)){
            return states[name]!!
        }
        states[name] = mutableStateOf(props.getProperty(name).trim('"'))
        return states[name]!!
    }

    override fun getString(name: String): String {
        return props.getProperty(name).trim('"')
    }
}