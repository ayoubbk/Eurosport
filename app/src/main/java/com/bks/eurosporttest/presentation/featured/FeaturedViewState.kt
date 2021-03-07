package com.bks.eurosporttest.presentation.featured

sealed class FeaturedViewState {

    object Content: FeaturedViewState()
    class Loading(val isLoading: Boolean): FeaturedViewState()
    class Error(val message:String): FeaturedViewState()
}


