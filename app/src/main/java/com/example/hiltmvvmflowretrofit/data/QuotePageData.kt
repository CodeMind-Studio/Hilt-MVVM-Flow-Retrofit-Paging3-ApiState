package com.example.hiltmvvmflowretrofit.data

data class QuotePageData(
    val count: Int,
    val lastItemIndex: Int,
    val page: Int,
    val results: List<QuoteData>,
    val totalCount: Int,
    val totalPages: Int
)