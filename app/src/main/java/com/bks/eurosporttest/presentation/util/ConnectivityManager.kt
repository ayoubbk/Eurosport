package com.bks.eurosporttest.presentation.util

import android.app.Application
import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ConnectivityManager
@Inject
constructor(appContext: Application)
{
    private val connectionObservable = ConnectionObservable(appContext)

    // expose this LiveData to observe it in ui
    val isNetworkAvailable = MutableLiveData<Boolean>()

    fun registerConnectionObserver(lifecycleOwner: LifecycleOwner) {
        connectionObservable.observe(lifecycleOwner, { isConnected ->
            isConnected?.let { isNetworkAvailable.value = it }
        })
    }

    fun unregisterConnectionObserver(lifecycleOwner: LifecycleOwner) {
        connectionObservable.removeObservers(lifecycleOwner)
    }
}