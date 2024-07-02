package com.practicum.pl_maker

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate


class App : Application() {

    private var darkTheme = false

    override fun onCreate() {
        super.onCreate()

        val sharedPrefs = getSharedPreferences(THEME, MODE_PRIVATE)

        darkTheme = sharedPrefs.getBoolean(THEME_KEY, false)
        switchTheme(darkTheme)
    }

    fun switchTheme(darkThemeEnabled: Boolean) {
        darkTheme = darkThemeEnabled
        val sharedPrefs = getSharedPreferences(THEME, MODE_PRIVATE)

        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled) {
                sharedPrefs
                    .edit()
                    .putBoolean(THEME_KEY, darkTheme)
                    .apply()
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                sharedPrefs
                    .edit()
                    .putBoolean(THEME_KEY, darkTheme)
                    .apply()
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }

    private companion object {
        const val THEME = "theme"
        const val THEME_KEY = "key"
    }
}

