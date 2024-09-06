package com.practicum.pl_maker.data.network

import com.practicum.pl_maker.data.NetworkClient
import com.practicum.pl_maker.data.dto.Response
import com.practicum.pl_maker.data.dto.TrackSearchRequest
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitNetworkClient : NetworkClient {

    private val iTunesBaseUrl = "https://itunes.apple.com"

    private val retrofit = Retrofit.Builder()
        .baseUrl(iTunesBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val iTunesService = retrofit.create(ITunesSearchApi::class.java)

    override fun doRequest(dto: Any): Response {
        return if (dto is TrackSearchRequest) {
            val resp = iTunesService.search(dto.expression).execute()
            if (resp.code() == 200) {
                val body = resp.body() ?: Response(resp.code())
                body.apply { resultCode = resp.code() }
            } else Response(resp.code())

        } else {
            Response(400)
        }
    }
}