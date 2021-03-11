package com.bks.eurosporttest.presentation.featured

sealed class FeaturedViewState {

    class Loading(val isLoading: Boolean): FeaturedViewState()
    class Error(val message: String): FeaturedViewState()
    class NetworkError(val idRes: Int): FeaturedViewState()
}


