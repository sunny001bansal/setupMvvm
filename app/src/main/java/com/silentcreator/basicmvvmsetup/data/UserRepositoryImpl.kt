package com.silentcreator.basicmvvmsetup.data

import com.silentcreator.basicmvvmsetup.domain.model.UserDataItem
import com.silentcreator.basicmvvmsetup.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val apiInterFace: ApiInterFace): UserRepository {
    override suspend fun getUserDetails(): Result<List<UserDataItem>> {
        return try {
            val response = apiInterFace.getUserDetails()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                val domainUsers = body.map { it.toDomain() }
                Result.success(domainUsers)
            } else {
                Result.failure(Exception("Failed to fetch user details: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}