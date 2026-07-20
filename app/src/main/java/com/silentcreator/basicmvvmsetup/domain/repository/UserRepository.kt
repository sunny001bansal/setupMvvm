package com.silentcreator.basicmvvmsetup.domain.repository

import com.silentcreator.basicmvvmsetup.domain.model.UserDataItem

interface UserRepository {
 suspend fun getUserDetails(): Result<List<UserDataItem>>
}