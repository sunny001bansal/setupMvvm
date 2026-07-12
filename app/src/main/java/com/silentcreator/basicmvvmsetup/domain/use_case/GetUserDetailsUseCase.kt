package com.silentcreator.basicmvvmsetup.domain.use_case

import com.silentcreator.basicmvvmsetup.domain.model.UserData
import com.silentcreator.basicmvvmsetup.domain.repository.UserRepository
import javax.inject.Inject

class GetUserDetailsUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(): Result<UserData> {
        return repository.fetchUserDetails()
    }
}
