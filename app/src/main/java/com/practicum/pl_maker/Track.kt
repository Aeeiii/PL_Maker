package com.practicum.pl_maker

data class Track(
    val trackName: String = "",
    val artistName: String = "",
    val trackTimeMillis: String = "",
    val artworkUrl100: String = "",
    val trackId: Int = 0,
    val collectionName : String = "",
    val releaseDate: String = "",
    val primaryGenreName: String = "",
    val country: String = ""
) {
    fun getCoverArtwork() = artworkUrl100.replaceAfterLast('/',"512x512bb.jpg")
    fun getYear() = releaseDate.substringBefore('-')
}