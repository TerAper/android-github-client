package com.aper.data.model

import com.squareup.moshi.Json

data class LogInUserEmailDto(
    @Json(name = "email") val email: String,
    @Json(name = "primary") val primary: Boolean,
    @Json(name = "verified") val verified: Boolean,
    @Json(name = "visibility") val visibility: String?
)