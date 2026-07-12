package com.silentcreator.basicmvvmsetup.data.mapper

import com.silentcreator.basicmvvmsetup.data.remote.dto.UserDataDto
import com.silentcreator.basicmvvmsetup.domain.model.UserData

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
