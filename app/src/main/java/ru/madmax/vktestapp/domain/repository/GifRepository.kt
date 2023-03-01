package ru.madmax.vktestapp.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.madmax.vktestapp.domain.model.Response
import ru.madmax.vktestapp.domain.model.SimpleResponse

interface GifRepository {

    suspend fun getTrendingGifs(
        apiKey: String,
        limit: Int = 25,
        rating: String = "g"
    ): Flow<Response>

    suspend fun search(
        apiKey: String,
        query: String,
        limit: Int = 25,
        offset: Int = 0,
        rating: String = "g"
    ): Flow<Response>

    suspend fun getById(
        id: String,
        apiKey: String
    ): Flow<SimpleResponse>
}