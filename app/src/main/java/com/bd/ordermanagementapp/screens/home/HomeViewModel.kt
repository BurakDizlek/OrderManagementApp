package com.bd.ordermanagementapp.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bd.data.repository.CampaignRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(private val campaignRepository: CampaignRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiViewState())
    val uiState: StateFlow<HomeUiViewState> = _uiState.asStateFlow()

    fun fetchCampaigns() {
        viewModelScope.launch {
            _uiState.update { it.copy(loadingCampaigns = true) }
            try {
                val data = campaignRepository.getAllCampaigns()
                _uiState.update { it.copy(campaigns = data, loadingCampaigns = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(errorCampaigns = e.message, loadingCampaigns = false) }
            }
        }
    }
}