package com.silentcreator.basicmvvmsetup.data.remote

import com.silentcreator.basicmvvmsetup.data.remote.dto.UserDataDto
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {
    @GET("api/")
    suspend fun getUserDetails(): Response<UserDataDto>
}
