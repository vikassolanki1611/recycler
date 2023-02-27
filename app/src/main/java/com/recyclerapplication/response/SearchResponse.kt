package com.recyclerapplication.response


import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("Response")
    val response: String,
    @SerializedName("Search")
    val search: ArrayList<Search>,
    @SerializedName("totalResults")
    val totalResults: String
)