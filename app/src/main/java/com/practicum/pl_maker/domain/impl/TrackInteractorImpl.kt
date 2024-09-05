package com.practicum.pl_maker.domain.impl

import com.practicum.pl_maker.domain.api.TrackRepository
import com.practicum.pl_maker.domain.api.TrackSharedPref
import com.practicum.pl_maker.domain.api.TracksInteractor
import com.practicum.pl_maker.domain.models.Track
import java.util.concurrent.Executors

class TrackInteractorImpl (private val repository: TrackRepository, private val sharedPrefs: TrackSharedPref) : TracksInteractor {

    private val executor = Executors.newCachedThreadPool()

    override fun searchTracks(expression: String, consumer: TracksInteractor.TracksConsumer) {
        executor.execute {
            try {
                consumer.consume(repository.searchTracks(expression), repository.getResultCode())
            } catch (t: Throwable) {
                consumer.onFailure(t)
            }

        }
    }

    override fun getSavedTracks(): ArrayList<Track> {
        return sharedPrefs.getSavedTracks()
    }

    override fun saveTrack(track: Track) {
        sharedPrefs.saveTrack(track)
    }

    override fun cleanHistory() {
        sharedPrefs.cleanHistory()
    }

}