package ru.madmax.vktestapp.presentation.mainScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.madmax.vktestapp.domain.model.GifItem
import ru.madmax.vktestapp.domain.useCase.GifUseCases
import ru.madmax.vktestapp.util.Constants.API_KEY
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val gifUseCases: GifUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(ListScreenState())
    val uiState = _uiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<String>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    isLoading = true
                )
            }
            gifUseCases
                .getTrendingUseCase(API_KEY, 25, "g")
                .catch { e ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            isLoading = false,
                            isError = true
                        )
                    }
                    _eventFlow.emit(e.localizedMessage ?: "Ошибка при загрузке")
                }
                .collectLatest { response ->
                    if (response.meta.status == 200) {
                        _uiState.update { currentState ->
                            currentState.copy(
                                list = response.data.map { it.toGifItem() },
                                totalCounts = response.pagination.total_count,
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

    fun updateSearchBarState(text: String) {
        _uiState.update { currentState ->
            currentState.copy(
                queryFieldText = text
            )
        }
    }

    fun loadNext() {
        viewModelScope.launch {
            if (uiState.value.queryFieldText != "" && uiState.value.offset < uiState.value.totalCounts) {
                val newList = mutableListOf<GifItem>()
                newList.addAll(_uiState.value.list)
                gifUseCases.searchUseCase(
                    API_KEY,
                    uiState.value.queryFieldText,
                    25,
                    uiState.value.offset,
                    "g"
                ).catch { e ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            isLoading = false,
                            isError = true
                        )
                    }
                    _eventFlow.emit(e.localizedMessage ?: "Ошибка при загрузке")
                }.collectLatest { response ->
                    if (response.meta.status == 200) {
                        newList.addAll(response.data.map { it.toGifItem() })
                        _uiState.update { currentState ->
                            currentState.copy(
                                list = newList,
                                totalCounts = response.pagination.total_count,
                                isLoading = false,
                                offset = response.pagination.offset + 25
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


    fun search() {
        viewModelScope.launch {
            if (uiState.value.queryFieldText != "") {
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = true
                    )
                }
                gifUseCases.searchUseCase(
                    API_KEY,
                    uiState.value.queryFieldText,
                    25,
                    0,
                    "g"
                ).catch { e ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            isLoading = false,
                            isError = true
                        )
                    }
                    _eventFlow.emit(e.localizedMessage ?: "Ошибка при загрузке")
                }.collectLatest { response ->
                    if (response.meta.status == 200) {
                        _uiState.update { currentState ->
                            currentState.copy(
                                list = response.data.map { it.toGifItem() },
                                totalCounts = response.pagination.total_count,
                                isLoading = false,
                                offset = response.pagination.offset + 25
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