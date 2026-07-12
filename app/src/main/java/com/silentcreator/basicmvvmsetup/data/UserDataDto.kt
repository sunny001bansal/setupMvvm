package com.silentcreator.basicmvvmsetup.data


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

fun UserDataDto.toDomain(): UserData {
    return UserData(
        results = results.map {
            UserData.Result(
                email = it.email,
                name = UserData.Result.Name(
                    first = it.name.first,
                    last = it.name.last
                ),
                picture = UserData.Result.Picture(
                    large = it.picture.large
                )
            )
        }
    )
}