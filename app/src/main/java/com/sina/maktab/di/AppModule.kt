package com.sina.maktab.di

import com.sina.maktab.data.network.FlickrService
import com.sina.maktab.data.network.FlickrService.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideInterceptor(): Interceptor = Interceptor { chain ->
        val url = chain.request()
            .url
            .newBuilder()
            .addQueryParameter("api_key", FlickrService.API_KEY)
            .addQueryParameter("method", FlickrService.METHOD)
            .addQueryParameter("user_id", FlickrService.USER_ID)
            .addQueryParameter("extras", FlickrService.EXTRAS)
            .addQueryParameter("format", FlickrService.FORMAT)
            .addQueryParameter("nojsoncallback", FlickrService.JSON_CALL_BACK)
            .addQueryParameter("per_page", FlickrService.PER_PAGE)
            .build()
        val request = chain.request()
            .newBuilder()
            .url(url)
            .build()
        chain.proceed(request)
    }

    @Provides
    @Singleton
    fun provideOkkHttpClient(interceptor: Interceptor): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .connectTimeout(5, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideCountryApi(retrofit: Retrofit): FlickrService =
        retrofit.create(FlickrService::class.java)
}