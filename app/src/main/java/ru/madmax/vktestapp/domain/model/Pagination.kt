package ru.madmax.vktestapp.domain.model

data class Pagination(
    val count: Int = 0,
    val offset: Int = 0,
    val total_count: Int = 0
)