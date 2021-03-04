package com.bks.eurosporttest.di

import com.bks.eurosporttest.data.network.ApiService
import com.bks.eurosporttest.data.network.mapper.VideoDtoMapper
import com.bks.eurosporttest.interactors.featured.GetVideosUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
object InteractorsModule {

    @ViewModelScoped
    @Provides
    fun provideGetVideos(
        apiService: ApiService,
        videoDtoMapper: VideoDtoMapper
    ):GetVideosUseCase {
        return GetVideosUseCase(
            apiService,
            videoDtoMapper
        )
    }

}