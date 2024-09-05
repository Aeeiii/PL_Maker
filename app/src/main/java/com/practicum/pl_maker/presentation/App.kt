package com.practicum.pl_maker.presentation

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.practicum.pl_maker.creator.Creator


class App : Application() {
    override fun onCreate() {
        super.onCreate()

        val settingsInteractorImpl = Creator.provideSettingsInteractor(this)

        setTheme(settingsInteractorImpl.getSaved())
    }

    fun switchTheme(darkThemeEnabled: Boolean) {

        val settingsInteractorImpl = Creator.provideSettingsInteractor(this)
        settingsInteractorImpl.change()

        setTheme(darkThemeEnabled)
    }

    private fun setTheme(darkThemeEnabled: Boolean) {
        AppCompatDelegate.setDefaultNightMode(if (darkThemeEnabled) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
    }
}

