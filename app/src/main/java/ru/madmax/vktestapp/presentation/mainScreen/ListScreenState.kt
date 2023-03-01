package ru.madmax.vktestapp.presentation.mainScreen

import ru.madmax.vktestapp.domain.model.GifItem

data class ListScreenState(
    val list: List<GifItem> = emptyList(),
    val queryFieldText: String = "",
    val offset: Int = 0,
    val totalCounts: Int = 0,
    val isLoading: Boolean = false,
    val isError: Boolean = false
)
