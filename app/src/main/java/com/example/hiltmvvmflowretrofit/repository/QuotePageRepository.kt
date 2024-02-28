package com.example.hiltmvvmflowretrofit.repository

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.hiltmvvmflowretrofit.data.QuoteData
import com.example.hiltmvvmflowretrofit.network.QuoteApiImp
import okio.IOException
import retrofit2.HttpException
import java.net.UnknownHostException
import javax.inject.Inject

class QuotePageRepository @Inject constructor(private val quoteApiImp: QuoteApiImp) :
    PagingSource<Int, QuoteData>() {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, QuoteData> {
        val page = params.key ?: 1
        return try {
            val result = quoteApiImp.getQuotePageData(page, params.loadSize)
            LoadResult.Page(
                result.results,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (result.results.isEmpty()) null else page + 1
            )
        } catch (e: UnknownHostException) {
            LoadResult.Error(e)
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, QuoteData>): Int? {
        TODO("Not yet implemented")
    }

}
