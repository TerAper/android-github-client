package com.aper.data.modle

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileReposDto(
    @SerialName("id") val id: Int,
    @SerialName("owner") val owner: OwnerDto,
    @SerialName("name") val name: String,
    @SerialName("description") val description: String? = null,
    @SerialName("language") val language: String? = null
)

@Serializable
data class OwnerDto(
    @SerialName("login") val login: String
)
