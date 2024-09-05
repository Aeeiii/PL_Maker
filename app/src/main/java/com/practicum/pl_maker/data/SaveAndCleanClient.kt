package com.practicum.pl_maker.data

interface SaveAndCleanClient {
    fun save(dto: Any)
    fun clean()
}