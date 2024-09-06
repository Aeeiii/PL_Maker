package com.practicum.pl_maker.data.dto

class TrackResponse(
    val results: List<TrackDto>, resultCode: Int
) : Response(resultCode)