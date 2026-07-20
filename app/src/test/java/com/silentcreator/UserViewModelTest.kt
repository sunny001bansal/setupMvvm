package com.silentcreator

import com.silentcreator.basicmvvmsetup.domain.model.UserDataItem
import com.silentcreator.basicmvvmsetup.domain.useCase.GetUserDetailsUsecase
import com.silentcreator.basicmvvmsetup.ui.UserViewModel
import com.silentcreator.basicmvvmsetup.ui.state.UiState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
class UserViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private val getUserDetailsUsecase: GetUserDetailsUsecase = mockk()
    private lateinit var viewModel: UserViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `load Test Data and verify success state`() = runTest {
        // Arrange
        val mockUser1 = mockk<UserDataItem> {
            every { id } returns 1
            every { name } returns "Alice"
        }
        val mockUser2 = mockk<UserDataItem> {
            every { id } returns 2
            every { name } returns "Bob"
        }
        val mockDomainData = listOf(mockUser1, mockUser2)

        coEvery { getUserDetailsUsecase() } returns Result.success(mockDomainData)

        // Act
        viewModel = UserViewModel(getUserDetailsUsecase)
        testDispatcher.scheduler.advanceUntilIdle() // Process internal launch coroutine blocks

        // Assert
        val state = viewModel.uiState.value
        assertTrue("State should be success", state is UiState.success)
        val successState = state as UiState.success
        assertEquals(2, successState.displayUser.size)
        assertEquals("Alice", successState.displayUser[0].name)

        // Optional verification check to ensure the use case was invoked exactly once
        coVerify(exactly = 1) { getUserDetailsUsecase() }
    }

    @Test
    fun `load Test Data and verify error state`() = runTest {
        // Arrange
        val errorMessage = "Network Error"
        coEvery { getUserDetailsUsecase() } returns Result.failure(Exception(errorMessage))

        // Act
        viewModel = UserViewModel(getUserDetailsUsecase)
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        val state = viewModel.uiState.value
        assertTrue("State should be error", state is UiState.error)
        assertEquals(errorMessage, (state as UiState.error).message)
    }
}
