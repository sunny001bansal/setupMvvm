package com.silentcreator.basicmvvmsetup.data.repository

import com.silentcreator.basicmvvmsetup.data.mapper.toDomain
import com.silentcreator.basicmvvmsetup.data.remote.ApiInterface
import com.silentcreator.basicmvvmsetup.domain.model.UserData
import com.silentcreator.basicmvvmsetup.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiInterface: ApiInterface
) : UserRepository {
    override suspend fun fetchUserDetails(): Result<UserData> = runCatching {
        val response = apiInterface.getUserDetails()
        if (response.isSuccessful) {
            response.body()?.toDomain() ?: throw Exception("Empty response")
        } else {
            throw Exception("Network request failed with ${response.code()}")
        }
    }
}
