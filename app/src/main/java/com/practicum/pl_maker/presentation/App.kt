package com.practicum.pl_maker.presentation

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import com.practicum.pl_maker.creator.Creator


class App : Application() {
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate() {
        super.onCreate()

        sharedPreferences = getSharedPreferences(THEME, MODE_PRIVATE)

        val settingsInteractorImpl = Creator.provideSettingsInteractor(sharedPreferences)

        setTheme(settingsInteractorImpl.getSaved())
    }

    fun switchTheme(darkThemeEnabled: Boolean) {

        val settingsInteractorImpl = Creator.provideSettingsInteractor(sharedPreferences)
        settingsInteractorImpl.change()

        setTheme(darkThemeEnabled)
    }

    private fun setTheme(darkThemeEnabled: Boolean) {
        AppCompatDelegate.setDefaultNightMode(if (darkThemeEnabled) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
    }

    private companion object {
        const val THEME = "theme"
    }
}

