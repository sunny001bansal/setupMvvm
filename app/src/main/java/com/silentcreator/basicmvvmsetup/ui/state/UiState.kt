package com.silentcreator.basicmvvmsetup.ui.state

import com.silentcreator.basicmvvmsetup.domain.data.UserDataItem

sealed interface UiState {
    object loading: UiState
    data class success(val displayUser: List<UserDataItem>): UiState
    data class error(val message: String): UiState
}