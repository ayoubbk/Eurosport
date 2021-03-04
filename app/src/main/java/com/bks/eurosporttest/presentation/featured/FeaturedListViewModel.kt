package com.bks.eurosporttest.presentation.featured

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bks.eurosporttest.domain.model.Video
import com.bks.eurosporttest.interactors.featured.GetVideosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeaturedListViewModel
@Inject
constructor(
    private val getVideosUseCase: GetVideosUseCase
):ViewModel()
{

    val loading = MutableLiveData<Boolean>()
    private val _videos = MutableLiveData<List<Video>>()
    val videos : LiveData<List<Video>>  = _videos

    init {
        fetchFeatured()
    }

    private fun fetchFeatured() {
        viewModelScope.launch {
            getVideosUseCase.execute().collect {dataState ->
                loading.value = dataState.loading

                dataState.data?.let { list ->
                    _videos.value = list
                }

                dataState.error?.let { message ->
                    //handle error
                }
            }
        }
    }


}