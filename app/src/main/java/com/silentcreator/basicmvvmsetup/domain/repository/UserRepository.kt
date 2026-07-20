package com.silentcreator.basicmvvmsetup.domain.repository

import com.silentcreator.basicmvvmsetup.domain.data.UserDataItem

interface UserRepository {
 suspend fun getUserDetails(): Result<List<UserDataItem>>
}