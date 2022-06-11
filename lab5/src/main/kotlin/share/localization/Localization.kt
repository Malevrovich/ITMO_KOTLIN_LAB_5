package share.localization

import androidx.compose.runtime.State

interface Localization {
    fun setLanguage(language: Language)
    fun getState(name: String): State<String>
    fun getString(name: String): String
}