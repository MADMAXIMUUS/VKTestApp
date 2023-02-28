package ru.madmax.vktestapp.domain.useCase

import ru.madmax.vktestapp.domain.repository.GifRepository

class SearchUseCase(private val gifRepository: GifRepository) {
    suspend operator fun invoke(
        apiKey: String,
        query: String,
        limit: Int,
        offset: Int,
        rating: String
    ) = gifRepository.search(apiKey, query, limit, offset, rating)
}