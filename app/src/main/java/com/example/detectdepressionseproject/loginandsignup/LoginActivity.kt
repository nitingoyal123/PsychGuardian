package com.example.detectdepressionseproject.loginandsignup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.detectdepressionseproject.CreateProfileActivity
import com.example.detectdepressionseproject.MainActivity
import com.example.detectdepressionseproject.R
import com.example.detectdepressionseproject.databinding.ActivityLoginBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    //collection name
    private var PATIENT_DETAIL_COLLECTION = "PatientDetails"

    //docRef
    private lateinit var docRef : DocumentReference

    //firestore
    private lateinit var db : FirebaseFirestore

    // firebase user
    private lateinit var user : FirebaseUser

    // binding
    private lateinit var binding : ActivityLoginBinding

    // firebase auth
    lateinit var auth : FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        FirebaseApp.initializeApp(this)
        db = Firebase.firestore
        binding = DataBindingUtil.setContentView(this@LoginActivity, R.layout.activity_login)
        var email = binding.edtLoginEmail.text.toString()
        var password = binding.edtLoginPassword.text.toString()

        binding.txtSignup.setOnClickListener {
            startActivity(Intent(this@LoginActivity, SignUpActivity::class.java))
        }

        binding.btnlogin.setOnClickListener {
            auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val user = auth.currentUser
                        checkProfileCreatedOrNot(email)
                    } else {
                        Toast.makeText(this@LoginActivity,"Invalid Id or password !!",Toast.LENGTH_SHORT).show()
                    }
                }.addOnFailureListener {
                    Log.d("LOGIN_ERROR",it.message.toString())
                    Toast.makeText(this@LoginActivity,"Something went wrong !!",Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun checkProfileCreatedOrNot(email: String) {

        docRef = db.collection(PATIENT_DETAIL_COLLECTION).document(email)

        docRef.get().addOnSuccessListener {
            if (it.exists()) {
                startActivity(Intent(this@LoginActivity,MainActivity::class.java))
            } else {
                startActivity(Intent(this@LoginActivity,CreateProfileActivity::class.java))
            }
        }
    }

}