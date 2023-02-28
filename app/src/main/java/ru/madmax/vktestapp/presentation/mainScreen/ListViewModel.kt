package ru.madmax.vktestapp.presentation.mainScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.madmax.vktestapp.domain.useCase.GifUseCases
import ru.madmax.vktestapp.util.Constants.API_KEY
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val gifUseCases: GifUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(ListScreenState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    isLoading = true
                )
            }
            gifUseCases
                .getTrendingUseCase(API_KEY, 25, "g")
                .collectLatest { response ->
                    if (response.meta.status == 200) {
                        _uiState.update { currentState ->
                            currentState.copy(
                                list = response.data,
                                isLoading = false
                            )
                        }
                    }else{
                        _uiState.update { currentState ->
                            currentState.copy(
                                isError = true,
                                errorMessage = response.meta.msg,
                                isLoading = false
                            )
                        }
                    }
                }
        }
    }
}