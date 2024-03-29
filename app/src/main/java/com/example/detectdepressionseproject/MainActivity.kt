package com.example.detectdepressionseproject

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.detectdepressionseproject.databinding.ActivityLoginBinding
import com.example.detectdepressionseproject.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        FirebaseApp.initializeApp(this);
        binding = DataBindingUtil.setContentView(this@MainActivity,R.layout.activity_main)

//        binding.cvTips.setOnClickListener {
//            moveToTipsActivity()
//        }
//
//        binding.cvProfile.setOnClickListener {
//            moveToProfileActivity()
//        }
//
//        binding.cvConsult.setOnClickListener {
//            moveToConsultActivity()
//        }
//
//        binding.cvQuestionnaire.setOnClickListener {
//            moveToInfoActivity()
//        }

    }

    private fun moveToInfoActivity() {
        var intent = Intent(this@MainActivity,InfoActivity::class.java)
    }
}