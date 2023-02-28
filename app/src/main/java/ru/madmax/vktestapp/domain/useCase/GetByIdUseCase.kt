package ru.madmax.vktestapp.domain.useCase

import ru.madmax.vktestapp.domain.repository.GifRepository

class GetByIdUseCase (private val gifRepository: GifRepository) {
    suspend operator fun invoke(id: String, apiKey: String) =
        gifRepository.getById(id, apiKey)
}