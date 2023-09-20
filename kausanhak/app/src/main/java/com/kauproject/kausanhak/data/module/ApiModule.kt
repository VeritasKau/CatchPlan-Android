package com.kauproject.kausanhak.data.module

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    private val OPEN_BASE_URL = "https://openapi.seoul.go.kr:8088/"
    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class SeoulPersonDataOkHttpClient

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class DefaultOkHttpClient

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class SeoulPersonDataRetrofit

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class DefaultRetrofit

    @SeoulPersonDataOkHttpClient
    @Provides
    @Singleton
    fun provideOpenAPIOkHttpClient(): OkHttpClient{
        return OkHttpClient.Builder()
            .build()
    }

    @SeoulPersonDataRetrofit
    @Provides
    @Singleton
    fun provideOpenAPIRetrofit(@SeoulPersonDataOkHttpClient client: OkHttpClient): Retrofit{
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        return Retrofit.Builder().client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(provideOpenAPIOkHttpClient())
            .baseUrl(OPEN_BASE_URL)
            .build()
    }



}