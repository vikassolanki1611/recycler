package com.recyclerapplication.webservice


import com.recyclerapplication.response.SearchResponse
import retrofit2.Response
import retrofit2.http.*


@JvmSuppressWildcards
interface ApiInterface {

    companion object {
        /*-------------------- BASE URL --------------------*/
        const val BASE_URL = " https://www.omdbapi.com"

    }



    /*=======================  APP API 's  ========================*/



    @GET("/")
    suspend fun getSearchData(
        @Query("apikey", encoded = false) apikey: String,
        @Query("s", encoded = false) search: String,
        @Query("type", encoded = false) type: String,
        @Query("page", encoded = false) paging: String?
    ): Response<SearchResponse>



}
