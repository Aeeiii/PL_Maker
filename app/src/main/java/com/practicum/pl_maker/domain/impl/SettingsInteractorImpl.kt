package com.practicum.pl_maker.domain.impl

import com.practicum.pl_maker.domain.api.SettingsInteractor
import com.practicum.pl_maker.domain.api.SettingsSharedPref

class SettingsInteractorImpl(private val sharedPrefs: SettingsSharedPref) : SettingsInteractor {
    override fun getSaved(): Boolean {
        return sharedPrefs.getSaved()
    }

    override fun change() {
        sharedPrefs.change()
    }

}