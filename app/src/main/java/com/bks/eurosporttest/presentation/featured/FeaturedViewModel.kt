package com.bks.eurosporttest.presentation.featured

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bks.eurosporttest.interactors.featured.GetVideosAndStories
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeaturedViewModel
@Inject
constructor(
    private val getVideosUseCase: GetVideosAndStories
):ViewModel()
{

    val loading = MutableLiveData<Boolean>()
    private val _videos = MutableLiveData<List<Any>>()
    val videos : LiveData<List<Any>> get() = _videos

    init {
        fetchFeatured()
    }

    private fun fetchFeatured() {
        viewModelScope.launch {
            getVideosUseCase.execute().collect {dataState ->
                loading.value = dataState.loading

                dataState.data?.let {
                    _videos.value = it
                }

                dataState.error?.let { message ->
                    //handle error
                }
            }
        }
    }


}