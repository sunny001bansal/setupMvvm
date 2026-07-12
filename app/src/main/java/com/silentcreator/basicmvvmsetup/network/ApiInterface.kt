package com.silentcreator.basicmvvmsetup.network

import com.silentcreator.basicmvvmsetup.data.UserDataDto
import retrofit2.Response
import retrofit2.http.GET

 interface ApiInterface {
    @GET("api/")
    suspend fun getUserDetails() : Response<UserDataDto>
}