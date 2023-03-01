package ru.madmax.vktestapp.presentation.detailsScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.madmax.vktestapp.domain.useCase.GifUseCases
import ru.madmax.vktestapp.util.Constants.API_KEY
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val gifUseCases: GifUseCases,
    state: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailsScreenState())
    val uiState = _uiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<String>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            state.get<String>("gifId").let { id ->
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = true
                    )
                }
                gifUseCases
                    .getByIdUseCase(id ?: "", API_KEY)
                    .catch { e ->
                        _uiState.update { currentState ->
                            currentState.copy(
                                isLoading = false,
                                isError = true
                            )
                        }
                        _eventFlow.emit(e.localizedMessage ?: "Ошибка при запросе")
                    }.collectLatest { response ->
                        if (response.meta.status == 200) {
                            _uiState.update { currentState ->
                                currentState.copy(
                                    title = response.data.title,
                                    id = response.data.id,
                                    gifUrl = response.data.images.original.url,
                                    rating = response.data.rating,
                                    userAvatarUrl = response.data.user.avatar_url,
                                    username = response.data.username,
                                    isLoading = false
                                )
                            }
                        } else {
                            _uiState.update { currentState ->
                                currentState.copy(
                                    isError = true,
                                    isLoading = false
                                )
                            }
                            _eventFlow.emit(response.meta.msg)
                        }
                    }
            }
        }
    }
}