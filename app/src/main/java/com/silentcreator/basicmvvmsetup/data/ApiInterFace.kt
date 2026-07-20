package com.silentcreator.basicmvvmsetup.data

import com.silentcreator.basicmvvmsetup.domain.data.UserDataItem
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterFace {

    @GET("users")
    suspend fun getUserDetails(): Response<List<UserDataItem>>
}