package ru.madmax.vktestapp.domain.useCase

data class GifUseCases(
    val getTrendingUseCase: GetTrendingUseCase,
    val searchUseCase: SearchUseCase,
    val getByIdUseCase: GetByIdUseCase
)