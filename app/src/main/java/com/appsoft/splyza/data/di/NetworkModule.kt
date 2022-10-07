package com.appsoft.splyza.data.di

import com.appsoft.splyza.data.network.MockTeamServiceApi
import com.appsoft.splyza.data.network.TeamServiceApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @Singleton
    @Provides
    fun provideRetrofitClient(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://google.com")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideTeamServiceApi(retrofit: Retrofit): TeamServiceApi {
        return retrofit.create(TeamServiceApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMockTeamServiceApi(dispatcher: CoroutineDispatcher): MockTeamServiceApi {
        return MockTeamServiceApi(dispatcher)
    }
}