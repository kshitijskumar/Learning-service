package com.example.learningservice

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.widget.Toast
import com.example.learningservice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var service : ServiceClass
    private var isBounded = false
    private var isServiceStarted = false

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as ServiceClass.MyServiceBinder
            this@MainActivity.service = binder.getService()
            isBounded = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBounded = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStartService.setOnClickListener {
            val intent = Intent(this, ServiceClass::class.java)
            intent.putExtra("name", "Kshitij")
            isServiceStarted = true
            startService(intent)
            bindToService()
        }

        binding.btnStopService.setOnClickListener {
            isBounded = false
            unbindService(connection)
            stopService(Intent(this, ServiceClass::class.java))
            isServiceStarted = false
        }

        binding.btnFetch.setOnClickListener {
            if(isBounded){
                Toast.makeText(this, "The random num fetched is ${service.sendRandomNum()}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if(isServiceStarted){
            bindToService()
        }
    }

    private fun bindToService() {
        val intent = Intent(this, ServiceClass::class.java)
        bindService(intent, connection, BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()
        unbindService(connection)
        isBounded = false
    }


}