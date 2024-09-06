package com.practicum.pl_maker.data.sharedPref

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.practicum.pl_maker.data.SavedSettingsClient
import com.practicum.pl_maker.data.dto.SharedPrefsSettings

class SettingsManager(context: Context) : SavedSettingsClient {
    private val sharedPrefs = context.getSharedPreferences(THEME, MODE_PRIVATE)
    override fun getSaved(): SharedPrefsSettings{
        return SharedPrefsSettings(sharedPrefs.getBoolean(THEME_KEY, false))
    }

    override fun change() {
        val savedTheme = getSaved()
        sharedPrefs.edit()
            .putBoolean(THEME_KEY, !(savedTheme.savedTheme))
            .apply()
    }

    private companion object {
        const val THEME = "theme"
        const val THEME_KEY = "key"
    }

}