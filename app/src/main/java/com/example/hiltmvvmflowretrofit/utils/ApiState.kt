package com.example.hiltmvvmflowretrofit.utils

import com.example.hiltmvvmflowretrofit.data.QuoteData

sealed class ApiState {
    data object Loading : ApiState()
    data object Empty : ApiState()
    class Error(val msg: Throwable) : ApiState()
    class Success(val data: List<QuoteData>) : ApiState()
}