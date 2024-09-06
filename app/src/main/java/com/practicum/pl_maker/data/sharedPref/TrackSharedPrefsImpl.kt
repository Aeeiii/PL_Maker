package com.practicum.pl_maker.data.sharedPref

import com.practicum.pl_maker.data.SavedTracksClient
import com.practicum.pl_maker.data.dto.SharedPrefsHistory
import com.practicum.pl_maker.data.dto.TrackDto
import com.practicum.pl_maker.domain.api.TrackSharedPref
import com.practicum.pl_maker.domain.models.Track

class TrackSharedPrefsImpl(
    private val savedDataClient: SavedTracksClient
): TrackSharedPref {
    override fun getSavedTracks(): ArrayList<Track> {
        val tracksListDto = savedDataClient.getSaved() as SharedPrefsHistory
        val trackList = ArrayList<Track>()
        trackList.addAll(
            tracksListDto.savedTracks.map {
                Track(
                    it.trackName,
                    it.artistName,
                    it.trackTimeMillis,
                    it.artworkUrl100,
                    it.trackId,
                    it.collectionName,
                    it.releaseDate,
                    it.primaryGenreName,
                    it.country,
                    it.previewUrl
                ) }
        )
        return trackList
    }

    override fun saveTrack(track: Track) {
        savedDataClient.save(
            TrackDto(
                trackName = track.trackName ?: return,
                artistName = track.artistName ?: return,
                trackTimeMillis = track.trackTimeMillis ?: return,
                artworkUrl100 = track.artworkUrl100 ?: return,
                collectionName = track.collectionName ?: return,
                releaseDate = track.releaseDate ?: return,
                primaryGenreName = track.primaryGenreName ?: return,
                country = track.country ?: return,
                previewUrl = track.previewUrl ?: return,
                trackId = track.trackId
            )
        )

    }

    override fun cleanHistory() {
        savedDataClient.clean()
    }
}