package com.example.detectdepressionseproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.detectdepressionseproject.databinding.ActivityCreateProfileBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class CreateProfileActivity : AppCompatActivity() {

    //firebase auth
    private lateinit var auth : FirebaseAuth

    //firebase user
    private lateinit var user : FirebaseUser

    //binding
    private lateinit var binding : ActivityCreateProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        FirebaseApp.initializeApp(this)
        binding = DataBindingUtil.setContentView(this@CreateProfileActivity,R.layout.activity_create_profile)

    }

    fun btnCreateProfile(view : View) {
        startActivity(Intent(this@CreateProfileActivity,EnterDetailsActivity::class.java))
    }

    override fun onStart() {
        super.onStart()
        auth = Firebase.auth
        user = auth.currentUser!!
    }

}