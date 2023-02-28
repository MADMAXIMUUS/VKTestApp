package ru.madmax.vktestapp.data.dataSource

import retrofit2.http.GET

interface GiftApi {

    @GET()
    suspend fun getTrending()
}