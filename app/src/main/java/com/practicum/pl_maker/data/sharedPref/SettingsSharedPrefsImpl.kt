package com.practicum.pl_maker.data.sharedPref

import com.practicum.pl_maker.domain.api.SettingsSharedPref

class SettingsSharedPrefsImpl(private val settingsManager: SettingsManager) : SettingsSharedPref {
    override fun getSaved(): Boolean {
        return settingsManager.getSaved().savedTheme
    }

    override fun change() {
        settingsManager.change()
    }
}