package com.example.learningservice

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.*
import java.util.Random

class ServiceClass : Service() {

    private var job: Job? = null
    private val binder = MyServiceBinder()

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("ServiceClass", "Service onCreate")
    }

    private val randomGenerator = Random()

    fun sendRandomNum() = randomGenerator.nextInt(100)

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("ServiceClass", "Service onStartCommand")
        intent?.let {
            Log.d("ServiceClass", "Service started with name ${it.getStringExtra("name")}")
        }

        job = CoroutineScope(Dispatchers.Main).launch {
            for(i in 0..60){
                Log.d("ServiceClass", "Running at $i")
                delay(1000L)
                if(i == 60){
                    stopSelf()
                }
            }
        }

        return super.onStartCommand(intent, flags, startId)
    }

    inner class MyServiceBinder : Binder() {
        fun getService() = this@ServiceClass
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
        Log.d("ServiceClass", "Service destroyed")
    }
}