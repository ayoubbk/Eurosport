package com.bks.eurosporttest.di

import com.bks.eurosporttest.data.network.ApiService
import com.bks.eurosporttest.data.network.mapper.StoryDtoMapper
import com.bks.eurosporttest.data.network.mapper.VideoDtoMapper
import com.bks.eurosporttest.interactors.featured.GetFeaturedItemUsecase
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
    fun provideGetVideosAndStories(
        apiService: ApiService,
        videoDtoMapper: VideoDtoMapper,
        storyDtoMapper: StoryDtoMapper
    ):GetFeaturedItemUsecase {
        return GetFeaturedItemUsecase(
            apiService,
            videoDtoMapper,
            storyDtoMapper
        )
    }

}