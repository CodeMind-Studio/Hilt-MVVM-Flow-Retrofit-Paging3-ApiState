package com.example.hiltmvvmflowretrofit.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.hiltmvvmflowretrofit.data.QuoteData
import com.example.hiltmvvmflowretrofit.network.QuoteApiImp
import com.example.hiltmvvmflowretrofit.repository.QuotePageRepository
import com.example.hiltmvvmflowretrofit.repository.Repo
import com.example.hiltmvvmflowretrofit.utils.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: Repo,
    private val quoteApiImp: QuoteApiImp
) : ViewModel() {

    private val quoteMutable: MutableStateFlow<ApiState> = MutableStateFlow(ApiState.Empty)
    val quoteDataFlow: StateFlow<ApiState> get() = quoteMutable

    fun getQuotes() = viewModelScope.launch {
        quoteMutable.value = ApiState.Loading
        repo.getData().catch { e ->
            quoteMutable.value = ApiState.Error(e)
        }.collect {
            quoteMutable.value = ApiState.Success(it)
        }
    }

    fun getQuotePageData(): Flow<PagingData<QuoteData>> =
        Pager(config = PagingConfig(10, enablePlaceholders = false)) {
            QuotePageRepository(quoteApiImp)
        }.flow.cachedIn(viewModelScope)

}