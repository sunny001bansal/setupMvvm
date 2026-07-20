package com.silentcreator.basicmvvmsetup.domain.useCase

import com.silentcreator.basicmvvmsetup.domain.data.UserDataItem
import com.silentcreator.basicmvvmsetup.domain.repository.UserRepository
import javax.inject.Inject


class GetUserDetailsUsecase @Inject constructor(private val userRepository: UserRepository) {

    suspend operator fun invoke() : Result<List<UserDataItem>>{
    return userRepository.getUserDetails()
    }
}