package com.example.hiltmvvmflowretrofit.network

import com.example.hiltmvvmflowretrofit.data.QuoteData
import com.example.hiltmvvmflowretrofit.data.QuotePageData
import retrofit2.http.GET
import retrofit2.http.Query

interface QuoteApi {

    @GET("quotes/random")
    suspend fun getQuoteFromServer(@Query("limit") limit: Int): List<QuoteData>

    @GET("quotes")
    suspend fun getQuotePage(@Query("page") page: Int, @Query("limit") limit: Int): QuotePageData
}