package ru.madmax.vktestapp.domain.model

data class GifItem(
    val id: String,
    val rating: String,
    val title: String,
    val url: String,
    val userAvatarUrl: String,
    val username: String,
)
