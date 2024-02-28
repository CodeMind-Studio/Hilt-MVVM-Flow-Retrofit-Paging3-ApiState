package com.example.hiltmvvmflowretrofit.repository

import com.example.hiltmvvmflowretrofit.data.QuoteData
import com.example.hiltmvvmflowretrofit.data.QuotePageData
import com.example.hiltmvvmflowretrofit.network.QuoteApiImp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class Repo @Inject constructor(private val quoteApiImp: QuoteApiImp) {

    fun getData(): Flow<List<QuoteData>> = flow {
        emit(quoteApiImp.getQuoteData(50))
    }.flowOn(Dispatchers.IO)
}