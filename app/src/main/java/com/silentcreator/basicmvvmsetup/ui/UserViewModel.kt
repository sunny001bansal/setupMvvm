package com.silentcreator.basicmvvmsetup.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.silentcreator.basicmvvmsetup.repository.UserRepository
import com.silentcreator.basicmvvmsetup.ui.state.UserUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<UserUiState>(UserUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        loadUser()
    }

    fun loadUser() {
        viewModelScope.launch {
            _uiState.value = UserUiState.Loading
            repository.fetchUserDetails()
                .onSuccess { _uiState.value = UserUiState.Success(it) }
                .onFailure { _uiState.value = UserUiState.Error(it.message ?: "Unknown Error") }
        }
    }

}
