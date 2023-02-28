package ru.madmax.vktestapp.data.dataSource

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.madmax.vktestapp.domain.model.Response

interface GifApi {

    @GET("/trending")
    fun getLatest(
        @Query("api_key") apiKey: String,
        @Query("limit") limit: Int,
        @Query("rating") rating: String
    ): Response

    @GET("/search")
    suspend fun search(
        @Query("api_key") apiKey: String,
        @Query("q") query: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("rating") rating: String
    ): Response

    @GET("/{id}")
    suspend fun getById(
        @Path("id") id: String,
        @Query("api_key") apiKey: String
    ): Response
}