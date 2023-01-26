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
        @Query("query") query: String, // query: 질의어
        @Query("sort") sort: String, // sort: 결과 문서 정렬 방식, accuracy(정확도순) 또는 recency(최신순), 기본값 accuracy
        @Query("page") page: Int, // 결과 페이지 번호 1~50, 기본 값 1
        @Query("size") size: Int // 한 페이지에 보여질 문서수 1~50, 기본 ㄱ밧 10
    ): Response<SearchResponse>
}