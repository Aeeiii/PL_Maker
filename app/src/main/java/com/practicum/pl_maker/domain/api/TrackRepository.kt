package com.practicum.pl_maker.domain.api

import com.practicum.pl_maker.domain.models.Track

interface TrackRepository {
    fun searchTracks(expression: String): List<Track>

    fun getResultCode(): Int
}