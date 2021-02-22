package com.example.learningservice

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.*

class ServiceClass : Service() {

    var job: Job? = null

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("ServiceClass", "Service onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("ServiceClass", "Service onStartCommand")
        intent?.let {
            Log.d("ServiceClass", "Service started with name ${it.getStringExtra("name")}")
        }

        job = CoroutineScope(Dispatchers.Main).launch {
            for(i in 0..10){
                Log.d("ServiceClass", "Running at $i")
                delay(1000L)
                if(i == 10){
                    stopSelf()
                }
            }
        }

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
        Log.d("ServiceClass", "Service destroyed")
    }
}