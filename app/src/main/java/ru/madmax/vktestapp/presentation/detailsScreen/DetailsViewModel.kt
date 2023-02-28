package ru.madmax.vktestapp.presentation.detailsScreen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.madmax.vktestapp.domain.useCase.GifUseCases
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val gifUseCases: GifUseCases
) : ViewModel() {

}