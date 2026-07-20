package com.silentcreator.basicmvvmsetup.data

import com.silentcreator.basicmvvmsetup.domain.data.UserDataItem
import com.silentcreator.basicmvvmsetup.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val apiInterFace: ApiInterFace): UserRepository {
    override suspend fun getUserDetails(): Result<List<UserDataItem>> {
        return try {
            val response = apiInterFace.getUserDetails()
            if (response.isSuccessful && response.body() !=null){
                Result.success(response.body()!!)
            }else{
                Result.failure(Exception("Failed to fetch user details"))
            }
        }catch (e: Exception){
            Result.failure(e)
        }
    }

}