package com.silentcreator.basicmvvmsetup.domain.repository

import com.silentcreator.basicmvvmsetup.domain.model.UserData

interface UserRepository {
    suspend fun fetchUserDetails(): Result<UserData>
}
