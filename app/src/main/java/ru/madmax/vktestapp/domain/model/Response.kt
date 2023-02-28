package ru.madmax.vktestapp.domain.model

data class Response(
    val data: List<Data>,
    val meta: Meta,
    val pagination: Pagination
)