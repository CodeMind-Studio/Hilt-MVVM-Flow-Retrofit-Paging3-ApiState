package com.example.hiltmvvmflowretrofit.network

import com.example.hiltmvvmflowretrofit.data.QuoteData
import javax.inject.Inject

class QuoteApiImp @Inject constructor(private val quoteApi: QuoteApi) {

    suspend fun getQuoteData(value: Int): List<QuoteData> = quoteApi.getQuoteFromServer(value)

    suspend fun getQuotePageData(pages : Int ,lim :Int) = quoteApi.getQuotePage(pages,lim)
}