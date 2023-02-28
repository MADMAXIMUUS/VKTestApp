package ru.madmax.vktestapp.presentation.mainScreen

import ru.madmax.vktestapp.domain.model.Data

data class ListScreenState(
    val list: List<Data> = emptyList(),
    val queryFieldText: String = "",
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)
