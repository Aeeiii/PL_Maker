package com.practicum.pl_maker.data.sharedPref

import com.practicum.pl_maker.data.SavedDataClient
import com.practicum.pl_maker.data.dto.SharedPrefsHistory
import com.practicum.pl_maker.data.dto.TrackDto
import com.practicum.pl_maker.domain.api.TrackSharedPref
import com.practicum.pl_maker.domain.models.Track

class TrackSharedPrefsImpl(
    private val savedDataClient: SavedDataClient
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
        val trackDto = track.trackName?.let {
            track.artistName?.let { it1 ->
                track.trackTimeMillis?.let { it2 ->
                    track.artworkUrl100?.let { it3 ->
                        track.collectionName?.let { it4 ->
                            track.releaseDate?.let { it5 ->
                                track.primaryGenreName?.let { it6 ->
                                    track.country?.let { it7 ->
                                        track.previewUrl?.let { it8 ->
                                            TrackDto(
                                                it,
                                                it1,
                                                it2,
                                                it3,
                                                track.trackId,
                                                it4,
                                                it5,
                                                it6,
                                                it7,
                                                it8
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (trackDto != null) {
            savedDataClient.save(trackDto)
        }

    }

    override fun cleanHistory() {
        savedDataClient.clean()
    }
}