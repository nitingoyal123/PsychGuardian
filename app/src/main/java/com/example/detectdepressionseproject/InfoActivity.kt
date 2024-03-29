package com.example.detectdepressionseproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.detectdepressionseproject.databinding.ActivityInfoBinding
import com.example.detectdepressionseproject.loginandsignup.LoginActivity
import com.google.firebase.FirebaseApp

class InfoActivity : AppCompatActivity() {

    private lateinit var binding : ActivityInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this@InfoActivity,R.layout.activity_info)
    }

    fun btnNext(view : View) {
        startActivity(Intent(this@InfoActivity,EnterDetailsActivity::class.java))
    }

}