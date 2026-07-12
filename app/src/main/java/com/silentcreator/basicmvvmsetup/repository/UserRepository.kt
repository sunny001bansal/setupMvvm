package com.silentcreator.basicmvvmsetup.repository

import com.silentcreator.basicmvvmsetup.data.UserData

interface UserRepository {
    suspend fun fetchUserDetails(): Result<UserData>
}