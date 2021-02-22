package com.example.learningservice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.learningservice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStartService.setOnClickListener {
            val intent = Intent(this, ServiceClass::class.java)
            intent.putExtra("name", "Kshitij")
            startService(intent)
        }

        binding.btnStopService.setOnClickListener {
            stopService(Intent(this, ServiceClass::class.java))
        }
    }
}