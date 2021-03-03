package com.bks.eurosporttest.di

import com.bks.eurosporttest.data.network.ApiService
import com.bks.eurosporttest.data.network.mapper.StoryDtoMapper
import com.bks.eurosporttest.data.network.mapper.VideoDtoMapper
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideVideoMapper(): VideoDtoMapper {
        return VideoDtoMapper()
    }

    @Singleton
    @Provides
    fun provideStoryMapper(): StoryDtoMapper {
        return StoryDtoMapper()
    }

    @Singleton
    @Provides
    fun provideApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl("https://extendsclass.com/api/json-storage/bin/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(ApiService::class.java)
    }
}