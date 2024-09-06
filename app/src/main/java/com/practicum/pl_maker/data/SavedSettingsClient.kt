package com.practicum.pl_maker.data

import com.practicum.pl_maker.data.dto.SharedPrefsSettings

interface SavedSettingsClient {
    fun getSaved(): SharedPrefsSettings
    fun change()

}