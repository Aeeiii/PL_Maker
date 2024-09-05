package com.practicum.pl_maker.domain.api

import com.practicum.pl_maker.domain.models.Track

interface TrackSharedPref {
    fun getSavedTracks(): ArrayList<Track>
    fun saveTrack(track: Track)
    fun cleanHistory()
}