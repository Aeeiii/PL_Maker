package com.practicum.pl_maker.domain.api

import com.practicum.pl_maker.domain.models.Track

interface TracksInteractor {
    fun searchTracks(expression: String, consumer: TracksConsumer)
    fun getSavedTracks(): List<Track>
    fun saveTrack(track: Track)
    fun cleanHistory()

    interface TracksConsumer {
        fun consume(foundTracks: List<Track>, resultCode: Int)
        fun onFailure(t: Throwable)
    }
}