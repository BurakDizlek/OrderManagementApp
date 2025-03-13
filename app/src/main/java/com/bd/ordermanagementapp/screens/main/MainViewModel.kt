package com.bd.ordermanagementapp.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bd.ordermanagementapp.data.repository.BottomBarRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(private val bottomBarRepository: BottomBarRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiViewState())
    val uiState: StateFlow<MainUiViewState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            bottomBarRepository.updateBottomBar()
            bottomBarRepository.bottomBarStartDestinationRoute.collectLatest { route ->
                _uiState.update {
                    it.copy(bottomBarStartDestinationRoute = route)
                }
            }
        }

        viewModelScope.launch {
            bottomBarRepository.bottomBarOptionsFlow.collectLatest { bottomBarOptions ->
                _uiState.update {
                    it.copy(bottomBarScreens = bottomBarOptions)
                }
            }
        }
    }
}