package com.practicum.pl_maker.domain.api

interface SettingsSharedPref {
    fun getSaved(): Boolean
    fun change()
}