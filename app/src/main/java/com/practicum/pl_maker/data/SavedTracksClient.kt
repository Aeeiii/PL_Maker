package com.practicum.pl_maker.data

import com.practicum.pl_maker.data.dto.SharedPrefsHistory

interface SavedTracksClient {
    fun getSaved(): SharedPrefsHistory
    fun save(dto: Any)
    fun clean()
}