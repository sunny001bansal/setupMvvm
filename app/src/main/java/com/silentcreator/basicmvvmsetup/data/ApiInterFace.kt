package com.silentcreator.basicmvvmsetup.data

import retrofit2.Response
import retrofit2.http.GET

interface ApiInterFace {

    @GET("users")
    suspend fun getUserDetails(): Response<List<UserDataDtoItem>>
}