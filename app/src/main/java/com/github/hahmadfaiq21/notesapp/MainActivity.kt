package com.github.hahmadfaiq21.notesapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.hahmadfaiq21.notesapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}