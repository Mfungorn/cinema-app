package com.example.cinema.core.models

import com.google.gson.annotations.SerializedName

data class Film(
    val id: Long,
    @SerializedName("localized_name")
    val localizedName: String,
    val name: String,
    val year: Int,
    val rating: Double?,
    @SerializedName("image_url")
    val imageUrl: String?,
    val description: String,
    val genres: ArrayList<String>
)