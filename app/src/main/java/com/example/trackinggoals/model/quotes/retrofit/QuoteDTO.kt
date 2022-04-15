package com.example.trackinggoals.model.quotes.retrofit

import com.google.gson.annotations.SerializedName

data class QuoteDTO(
    @SerializedName("author")
    val author: Author? = null,
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("text")
    val text: String? = null
)

data class Author(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("name")
    val name: String? = null
)