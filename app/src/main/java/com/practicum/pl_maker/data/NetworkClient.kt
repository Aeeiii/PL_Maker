package com.practicum.pl_maker.data

import com.practicum.pl_maker.data.dto.Response

interface NetworkClient {
    fun doRequest(dto: Any): Response

}