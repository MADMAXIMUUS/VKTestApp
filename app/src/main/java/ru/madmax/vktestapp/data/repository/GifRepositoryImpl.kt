package ru.madmax.vktestapp.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.madmax.vktestapp.data.dataSource.GifApi
import ru.madmax.vktestapp.domain.model.Response
import ru.madmax.vktestapp.domain.repository.GifRepository

class GifRepositoryImpl(private val gifApi: GifApi) : GifRepository {

    override suspend fun getTrendingGifs(
        apiKey: String,
        limit: Int,
        rating: String
    ): Flow<Response> = flow {
        emit(gifApi.getLatest(apiKey, limit, rating))
    }

    override suspend fun search(
        apiKey: String,
        query: String,
        limit: Int,
        offset: Int,
        rating: String
    ): Flow<Response> = flow {
        emit(gifApi.search(apiKey, query, limit, offset, rating))
    }

    override suspend fun getById(id: String, apiKey: String): Flow<Response> = flow {
        emit(gifApi.getById(id, apiKey))
    }
}