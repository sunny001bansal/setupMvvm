package com.silentcreator.basicmvvmsetup

import com.silentcreator.basicmvvmsetup.data.UserData
import com.silentcreator.basicmvvmsetup.repository.UserRepository
import com.silentcreator.basicmvvmsetup.ui.UserViewModel
import com.silentcreator.basicmvvmsetup.ui.state.UserUiState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UserViewModelTest {
    private val repository = mockk<UserRepository>()
    private lateinit var viewModel: UserViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadUser emits Success state when repository succeeds`() = runTest(testDispatcher) {
        val mockUser = UserData(
            results = listOf(
                UserData.Result(
                    email = "john@example.com",
                    name = UserData.Result.Name("John", "Doe"),
                    picture = UserData.Result.Picture("")
                )
            )
        )
        coEvery { repository.fetchUserDetails() } returns Result.success(mockUser)

        viewModel = UserViewModel(repository) // triggers init which calls loadUser()
        
        advanceUntilIdle()

        assertEquals(UserUiState.Success(mockUser), viewModel.uiState.value)
    }

    @Test
    fun `loadUser emits Error state when repository fails`() = runTest(testDispatcher) {
        val errorMessage = "Network Error"
        coEvery { repository.fetchUserDetails() } returns Result.failure(Exception(errorMessage))

        viewModel = UserViewModel(repository)
        
        advanceUntilIdle()

        assertEquals(UserUiState.Error(errorMessage), viewModel.uiState.value)
    }
}
