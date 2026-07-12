package com.silentcreator.basicmvvmsetup.ui.state

import com.silentcreator.basicmvvmsetup.domain.model.UserData

sealed interface UserUiState {
    object Loading : UserUiState
    data class Success(val user: UserData) : UserUiState
    data class Error(val message: String) : UserUiState
}
