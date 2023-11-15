package com.kauproject.kausanhak.data.module

import android.content.Context
import com.kauproject.kausanhak.data.remote.AppInterceptor
import com.kauproject.kausanhak.data.remote.service.chat.ChatService
import com.kauproject.kausanhak.data.remote.service.event.GetEventService
import com.kauproject.kausanhak.data.remote.service.info.GetUserInfoService
import com.kauproject.kausanhak.data.remote.service.info.InformSaveService
import com.kauproject.kausanhak.data.remote.service.login.CheckMemberService
import com.kauproject.kausanhak.data.remote.service.login.DeleteUserService
import com.kauproject.kausanhak.data.remote.service.login.SignInService
import com.kauproject.kausanhak.data.remote.service.scrap.ScrapSignService
import com.kauproject.kausanhak.presentation.util.ApplicationClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    companion object{
        const val BASE_URL = "http://catchplan-env.eba-ngqypwbe.ap-northeast-2.elasticbeanstalk.com"
        const val CHAT_URL = "http://43.201.223.94/"
    }

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class BaseRetrofit

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class ChatRetrofit


    @ChatRetrofit
    @Singleton
    @Provides
    fun getChatOkHttpClient(): OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .connectTimeout(40, TimeUnit.SECONDS)
            .readTimeout(40, TimeUnit.SECONDS)
            .writeTimeout(40, TimeUnit.SECONDS)
            .build()
    }

    @ChatRetrofit
    @Singleton
    @Provides
    fun getChatInstance(@ChatRetrofit okHttpClient: OkHttpClient): Retrofit{
        return Retrofit.Builder().client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(getChatOkHttpClient())
            .baseUrl(BASE_URL)
            .build()
    }

    @BaseRetrofit
    @Singleton
    @Provides
    fun getInterceptor(): AppInterceptor{
        return AppInterceptor()
    }

    @BaseRetrofit
    @Singleton
    @Provides
    fun getOkHttpClient(@BaseRetrofit interceptor: AppInterceptor): OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @BaseRetrofit
    @Singleton
    @Provides
    fun getInstance(@BaseRetrofit okHttpClient: OkHttpClient): Retrofit{
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        return Retrofit.Builder().client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(getOkHttpClient(getInterceptor()))
            .baseUrl(BASE_URL)
            .build()
    }

    @Singleton
    @Provides
    fun provideSignInService(@BaseRetrofit retrofit: Retrofit): SignInService{
        return retrofit.create(SignInService::class.java)
    }

    @Singleton
    @Provides
    fun provideInfoSaveService(@BaseRetrofit retrofit: Retrofit): InformSaveService{
        return retrofit.create(InformSaveService::class.java)
    }

    @Singleton
    @Provides
    fun provideDeleteUserService(@BaseRetrofit retrofit: Retrofit): DeleteUserService{
        return retrofit.create(DeleteUserService::class.java)
    }

    @Singleton
    @Provides
    fun provideGetEventService(@BaseRetrofit retrofit: Retrofit): GetEventService{
        return retrofit.create(GetEventService::class.java)
    }

    @Singleton
    @Provides
    fun provideCheckMember(@BaseRetrofit retrofit: Retrofit): CheckMemberService{
        return retrofit.create(CheckMemberService::class.java)
    }

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): ApplicationClass{
        return app as ApplicationClass
    }

    @Singleton
    @Provides
    fun provideGetUserInfo(@BaseRetrofit retrofit: Retrofit): GetUserInfoService{
        return retrofit.create(GetUserInfoService::class.java)
    }

    @Singleton
    @Provides
    fun provideScrapSign(@BaseRetrofit retrofit: Retrofit): ScrapSignService{
        return retrofit.create(ScrapSignService::class.java)
    }

    @Singleton
    @Provides
    fun provideChat(@ChatRetrofit retrofit: Retrofit): ChatService{
        return retrofit.create(ChatService::class.java)
    }

}