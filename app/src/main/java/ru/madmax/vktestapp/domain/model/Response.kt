package ru.madmax.vktestapp.domain.model

data class Response(
    val data: List<Data> = emptyList(),
    val meta: Meta = Meta(),
    val pagination: Pagination = Pagination()
)