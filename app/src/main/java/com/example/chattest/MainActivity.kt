package com.example.chattest

import com.example.chattest.R.id
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.chattest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //val tUserName = findViewById<TextView>(id.txtUserName)
        //val userName = tUserName.text.toString()
        val userName = binding.txtUserName.text.toString()
        val email = binding.txtEmail.text.toString()
        val password = binding.txtPassword.text.toString()

        Log.d("MainActivity", "E-mail is: " + email)
        Log.d("MainActivity", "Password:  $password")
        Log.d("MainActivity", "UserName: " + userName)
    }
}