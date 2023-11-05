package com.kauproject.kausanhak.data.module

import android.content.Context
import com.kauproject.kausanhak.data.remote.AppInterceptor
import com.kauproject.kausanhak.data.remote.service.event.GetEventService
import com.kauproject.kausanhak.data.remote.service.info.InformSaveService
import com.kauproject.kausanhak.data.remote.service.login.CheckMemberService
import com.kauproject.kausanhak.data.remote.service.login.DeleteUserService
import com.kauproject.kausanhak.data.remote.service.login.SignInService
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
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    private val BASE_URL = "http://catchplan-env.eba-ngqypwbe.ap-northeast-2.elasticbeanstalk.com"

    @Singleton
    @Provides
    fun getInterceptor(): AppInterceptor{
        return AppInterceptor()
    }

    @Singleton
    @Provides
    fun getOkHttpClient(interceptor: AppInterceptor): OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @Singleton
    @Provides
    fun getInstance(okHttpClient: OkHttpClient): Retrofit{
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
    fun provideSignInService(retrofit: Retrofit): SignInService{
        return retrofit.create(SignInService::class.java)
    }

    @Singleton
    @Provides
    fun provideInfoSaveService(retrofit: Retrofit): InformSaveService{
        return retrofit.create(InformSaveService::class.java)
    }

    @Singleton
    @Provides
    fun provideDeleteUserService(retrofit: Retrofit): DeleteUserService{
        return retrofit.create(DeleteUserService::class.java)
    }

    @Singleton
    @Provides
    fun provideGetEventService(retrofit: Retrofit): GetEventService{
        return retrofit.create(GetEventService::class.java)
    }

    @Singleton
    @Provides
    fun provideCheckMember(retrofit: Retrofit): CheckMemberService{
        return retrofit.create(CheckMemberService::class.java)
    }

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): ApplicationClass{
        return app as ApplicationClass
    }

}