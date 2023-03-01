package ru.madmax.vktestapp.presentation.detailsScreen

data class DetailsScreenState(
    val title: String = "",
    val gifUrl: String = "",
    val id: String = "",
    val rating: String = "",
    val userAvatarUrl: String="",
    val username: String = "",
    val isLoading: Boolean = false,
    val isError: Boolean = false
)
