package com.bks.eurosporttest.presentation.featured

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bks.eurosporttest.R
import com.bks.eurosporttest.domain.model.FeaturedItem
import com.bks.eurosporttest.interactors.featured.GetFeaturedItemUsecase
import com.bks.eurosporttest.presentation.util.ConnectivityManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeaturedViewModel
@Inject
constructor(
private val getVideosUseCase: GetFeaturedItemUsecase,
private val connectivityManager: ConnectivityManager,
 ): ViewModel()

{
    private val _viewState = MutableLiveData<FeaturedViewState>()
    val viewState: LiveData<FeaturedViewState> = _viewState

    private val _videosAndStories = MutableLiveData<List<FeaturedItem>>()
    val videosAndStories : LiveData<List<FeaturedItem>> get() = _videosAndStories

    private val isNetworkAvailable: MutableLiveData<Boolean> = connectivityManager.isNetworkAvailable

    init {
        fetchFeatured()
    }

    fun fetchFeatured() {

        when(isNetworkAvailable.value) {
            true -> {
                viewModelScope.launch {
                    getVideosUseCase.execute().collect { dataState ->

                        dataState.loading.let {
                            _viewState.value = FeaturedViewState.Loading(it)
                        }

                        dataState.data?.let { list ->
                            _videosAndStories.value = list
                        }

                        dataState.error?.let { message ->
                            _viewState.value = FeaturedViewState.Error(message)
                        }
                    }
                }
            }

            false -> {
                _viewState.value = FeaturedViewState.NetworkError(R.string.no_internet_error)
            }
        }
    }


}