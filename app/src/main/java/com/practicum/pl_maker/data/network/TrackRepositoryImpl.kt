package com.practicum.pl_maker.data.network

import com.practicum.pl_maker.data.NetworkClient
import com.practicum.pl_maker.data.dto.TrackResponse
import com.practicum.pl_maker.data.dto.TrackSearchRequest
import com.practicum.pl_maker.domain.api.TrackRepository
import com.practicum.pl_maker.domain.models.Track

class TrackRepositoryImpl (private val networkClient: NetworkClient) : TrackRepository {

    private var resultCode = 0

    override fun searchTracks(expression: String): List<Track> {
        val response = networkClient.doRequest(TrackSearchRequest(expression))
        resultCode = response.resultCode
        if (response.resultCode == 200) {
            return (response as TrackResponse).results.map {
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
        } else {
            return emptyList()
        }
    }

    override fun getResultCode(): Int {
        return resultCode
    }


}
