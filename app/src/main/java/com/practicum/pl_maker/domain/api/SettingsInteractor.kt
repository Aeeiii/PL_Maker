package com.practicum.pl_maker.domain.api

interface SettingsInteractor {
    fun getSaved(): Boolean
    fun change()
}