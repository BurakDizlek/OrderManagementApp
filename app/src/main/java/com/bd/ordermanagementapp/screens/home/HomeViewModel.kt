package com.bd.ordermanagementapp.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bd.data.model.ResultCodes
import com.bd.data.repository.campaign.CampaignRepository
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
                val result = campaignRepository.getAllCampaigns()
                if (result.code == ResultCodes.SUCCESS) {
                    _uiState.update { it.copy(campaigns = result.data, loadingCampaigns = false) }
                } else {
                    _uiState.update {
                        it.copy(
                            errorCampaigns = result.message,
                            loadingCampaigns = false
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(errorCampaigns = e.message, loadingCampaigns = false) }
            }
        }
    }
}