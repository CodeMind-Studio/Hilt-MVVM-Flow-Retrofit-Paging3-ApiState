package com.example.hiltmvvmflowretrofit.di

import com.example.hiltmvvmflowretrofit.network.QuoteApi
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun getUrl(): String = "https://api.quotable.io/"


    @Provides
    @Singleton
    fun getRetrofitInstance(url : String): QuoteApi {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(Interceptor { chain ->
            val request: Request = chain.request().newBuilder().addHeader(
                "api-key",
                ""
            ).build()
            chain.proceed(request)

        }).addInterceptor(interceptor = logging).connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()

        val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .create()
        return Retrofit.Builder().baseUrl(url).client(client)
            .addConverterFactory(GsonConverterFactory.create(gson)).build()
            .create(QuoteApi::class.java)
    }

//    @Provides
//    @Singleton
//    fun getQuoteApi(url: String): QuoteApi {
//        return Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(QuoteApi::class.java)
//    }
}
