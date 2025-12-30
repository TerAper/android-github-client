package com.aper.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LogInUserEmailDto(
    @SerialName("email") val email: String,
    @SerialName("primary") val primary: Boolean,
    @SerialName("verified") val verified: Boolean,
    @SerialName("visibility") val visibility: String? = null
)
