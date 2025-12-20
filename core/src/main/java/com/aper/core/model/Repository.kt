package com.aper.core.model

data class Repository(
    val id: Int,
    val owner: String,
    val name: String,
    val description: String? ,
    val language: String? ,
)
