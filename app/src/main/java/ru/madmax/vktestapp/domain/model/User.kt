package ru.madmax.vktestapp.domain.model

data class User(
    val avatar_url: String = "",
    val banner_image: String = "",
    val banner_url: String = "",
    val description: String = "",
    val display_name: String = "",
    val instagram_url: String = "",
    val is_verified: Boolean = false,
    val profile_url: String = "",
    val username: String = "",
    val website_url: String = ""
)