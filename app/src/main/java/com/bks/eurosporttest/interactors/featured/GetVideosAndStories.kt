package com.bks.eurosporttest.interactors.featured

import android.util.Log
import com.bks.eurosporttest.data.network.ApiService
import com.bks.eurosporttest.data.network.mapper.StoryDtoMapper
import com.bks.eurosporttest.data.network.mapper.VideoDtoMapper
import com.bks.eurosporttest.data.network.response.NetworkResponse
import com.bks.eurosporttest.domain.state.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

private const val TAG = "GetVideosUseCase"

class GetVideosAndStories(
    private val apiService: ApiService,
    private val videoDtoMapper: VideoDtoMapper,
    private val storyDtoMapper: StoryDtoMapper
) {

    fun execute(): Flow<DataState<List<Any>>> = flow {
        try {
            emit(DataState.loading())

            // if network is available make the request otherwise emit error message
            val apiResult = getVideosAndStories()

            val videos = videoDtoMapper.toDomainList(apiResult.videos)
            val stories = storyDtoMapper.toDomainList(apiResult.stories)

            val sortedVideos = videos.sortedByDescending { it.date }
            val sortedStories = stories.sortedByDescending { it.date }

            val resultSize = (videos.size + stories.size) - 1
            val mixedList: ArrayList<Any> = ArrayList()
            for (i in 0 until resultSize ) {
                if(i < videos.size) {
                    mixedList.add(sortedVideos[i])
                }
                if(i < stories.size) {
                    mixedList.add(sortedStories[i])
                }
            }

            emit(DataState.success(mixedList))
        } catch (e: Exception) {
            emit(DataState.error<List<Any>>(e.message ?: "Unknown Error"))
        }
    }

    private suspend fun getVideosAndStories(): NetworkResponse {
        return apiService.getVideosAndStories()
    }


}