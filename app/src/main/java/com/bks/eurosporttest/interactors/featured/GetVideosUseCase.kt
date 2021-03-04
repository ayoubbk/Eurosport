package com.bks.eurosporttest.interactors.featured

import android.util.Log
import com.bks.eurosporttest.data.network.ApiService
import com.bks.eurosporttest.data.network.mapper.VideoDtoMapper
import com.bks.eurosporttest.domain.model.Video
import com.bks.eurosporttest.domain.state.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

private const val TAG = "GetVideosUseCase"

class GetVideosUseCase(
    private val apiService: ApiService,
    private val videoDtoMapper: VideoDtoMapper
) {

    fun execute(): Flow<DataState<List<Video>>> = flow {
        try {
            emit(DataState.loading())

            // if network is available make the request otherwise emit error message
            val videos = getVideosFromNetwork()
            emit(DataState.success(videos))
        } catch (e: Exception) {
            Log.e(TAG, "Error to get featured list :  $e.printStackTrace()")
            emit(DataState.error<List<Video>>(e.message ?: "Unknown Error"))
        }
    }

    private suspend fun getVideosFromNetwork(): List<Video> {
        return videoDtoMapper.toDomainList(
            apiService.getVideosAndStories().videos
        )
    }

}