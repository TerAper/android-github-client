package com.aper.data.modle

import com.squareup.moshi.Json

data class ProfileReposDto(
    @Json(name = "id") val id: Int,
    @Json(name = "owner") val owner: OwnerDto,
    @Json(name = "name") val name: String,
    @Json(name = "description") val description: String?,
    @Json(name = "language") val language: String?,
)

data class OwnerDto(
    val login: String
)
