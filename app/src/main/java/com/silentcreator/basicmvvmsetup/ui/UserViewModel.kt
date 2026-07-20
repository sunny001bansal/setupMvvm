package com.silentcreator.basicmvvmsetup.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.silentcreator.basicmvvmsetup.domain.model.UserDataItem
import com.silentcreator.basicmvvmsetup.domain.useCase.GetUserDetailsUsecase
import com.silentcreator.basicmvvmsetup.ui.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


@HiltViewModel
class UserViewModel @Inject constructor(private val getUserDetailsUsecase: GetUserDetailsUsecase) : ViewModel(){
    private val _uiState = MutableStateFlow<UiState>(UiState.loading)
    val uiState : StateFlow<UiState> = _uiState.asStateFlow()
    private var cachedUsers: List<UserDataItem> = emptyList<UserDataItem>()
    init {
        getUserDetails()
    }

    private fun getUserDetails() {
        viewModelScope.launch {
            _uiState.value = UiState.loading

            getUserDetailsUsecase()
                .onSuccess { userData ->
                    cachedUsers = userData
                    _uiState.value = UiState.success(displayUser = userData)
                }
                .onFailure { err ->
                    _uiState.value = UiState.error(message = err.message ?: "Unknown error")
                }
        }
    }

}