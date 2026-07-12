package com.silentcreator.basicmvvmsetup.repository

import com.silentcreator.basicmvvmsetup.data.UserData
import com.silentcreator.basicmvvmsetup.data.toDomain
import com.silentcreator.basicmvvmsetup.network.ApiInterface
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val apiInterface: ApiInterface): UserRepository {
    override suspend fun fetchUserDetails(): Result<UserData> = runCatching {
      val response = apiInterface.getUserDetails()
        if (response.isSuccessful){
            response.body()?.toDomain()?: throw Exception("Empty response")
        }else{
            throw Exception("Network request failed with ${response.code()}")
        }
    }
}