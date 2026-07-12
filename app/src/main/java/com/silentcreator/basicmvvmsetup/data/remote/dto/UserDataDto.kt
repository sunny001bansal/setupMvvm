package com.silentcreator.basicmvvmsetup.data.remote.dto

import com.google.gson.annotations.SerializedName

data class UserDataDto(
    @SerializedName("results")
    val results: List<Result>
) {
    data class Result(
        @SerializedName("email")
        val email: String,
        @SerializedName("name")
        val name: Name,
        @SerializedName("picture")
        val picture: Picture
    ) {
        data class Name(
            @SerializedName("first")
            val first: String,
            @SerializedName("last")
            val last: String
        )

        data class Picture(
            @SerializedName("large")
            val large: String
        )
    }
}
