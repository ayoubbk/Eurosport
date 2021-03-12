package com.bks.eurosporttest.interactors.app

import android.util.Log
import androidx.annotation.WorkerThread
import com.bks.eurosporttest.util.TAG
import java.io.IOException
import java.net.InetSocketAddress
import javax.net.SocketFactory

object HasInternetConnection {

    @WorkerThread
    fun execute(socketFactory: SocketFactory): Boolean {
        return try {
            val socket = socketFactory.createSocket() ?: throw IOException("Socket is null.")
            socket.connect(InetSocketAddress("8.8.8.8", 53), 1500)
            socket.close()
            Log.d(TAG, "PING success.")
            true
        } catch (e: IOException) {
            Log.e(TAG, "No internet connection. ${e}")
            false
        }
    }
}