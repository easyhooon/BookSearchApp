package com.kenshi.booksearchapp.data.api

import com.kenshi.booksearchapp.data.model.SearchResponse
import com.kenshi.booksearchapp.util.Constants.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface BookSearchApi {

    //api 호출할때는 REST API KEY 로
    @Headers("Authorization: KakaoAK $API_KEY")
    @GET("v3/search/book")
    suspend fun searchBooks(
        @Query("query") query: String,
        @Query("sort") sort: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): Response<SearchResponse>

}