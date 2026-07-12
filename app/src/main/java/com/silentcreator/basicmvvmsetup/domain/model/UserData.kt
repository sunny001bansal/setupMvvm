package com.silentcreator.basicmvvmsetup.domain.model

data class UserData(
    val results: List<Result>
) {
    data class Result(
        val email: String,
        val name: Name,
        val picture: Picture
    ) {
        data class Name(
            val first: String,
            val last: String
        )

        data class Picture(
            val large: String
        )
    }
}
