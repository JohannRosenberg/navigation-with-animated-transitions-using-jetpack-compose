package dev.wirespec.adoptme.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import dev.wirespec.adoptme.App

class NetworkUtils {
    companion object {
        var networkAvailable: Boolean = false

        fun startNetworkCallback() {
            val cm: ConnectivityManager = App.context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val builder: NetworkRequest.Builder = NetworkRequest.Builder()

            cm.registerNetworkCallback(
                builder.build(),
                object : ConnectivityManager.NetworkCallback() {

                    override fun onAvailable(network: Network) {
                        networkAvailable = true
                    }

                    override fun onLost(network: Network) {
                        networkAvailable = false
                    }
                })
        }
    }
}