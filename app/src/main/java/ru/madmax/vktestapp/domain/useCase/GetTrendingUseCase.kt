package ru.madmax.vktestapp.domain.useCase

import ru.madmax.vktestapp.domain.repository.GifRepository

class GetTrendingUseCase(private val gifRepository: GifRepository) {
    suspend operator fun invoke(apiKey: String, limit: Int, rating: String) =
        gifRepository.getTrendingGifs(apiKey, limit, rating)
}