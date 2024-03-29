package com.example.detectdepressionseproject.loginandsignup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.detectdepressionseproject.EnterDetailsActivity
import com.example.detectdepressionseproject.R
import com.example.detectdepressionseproject.databinding.ActivitySignUpBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {

    //firebase user
    private lateinit var user : FirebaseUser

    //binding
    private lateinit var binding : ActivitySignUpBinding

    //firebase auth
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        FirebaseApp.initializeApp(this);
        binding = DataBindingUtil.setContentView(this@SignUpActivity, R.layout.activity_sign_up)

        var email = ""
        var password = ""
        var confirmPassword = ""

        binding.apply {

            email = edtSignUpEmail.text.toString()
            password = edtSignUpPassword.text.toString()
            confirmPassword = edtSignUpConfirmPassword.text.toString()

            btnNext.setOnClickListener {
                signUp(email,password,confirmPassword)
            }

        }
    }

    private fun signUp(email: String, password: String, confirmPassword: String) {

        if (password.length < 6) {
            Toast.makeText(this@SignUpActivity,"At least 6 Characters are required for password !!",Toast.LENGTH_SHORT).show()
            return
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this@SignUpActivity,"Confirm Password should be equal to password !!",Toast.LENGTH_SHORT).show()
            return
        }

        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val user = auth.currentUser
                    val intent = Intent(this@SignUpActivity,EnterDetailsActivity::class.java)
                    startActivity(intent)
                }
            }.addOnFailureListener {
                Log.d("SIGNUP_ERROR",it.message.toString())
                Toast.makeText(this@SignUpActivity,"Authentication failed !!",Toast.LENGTH_SHORT).show()
            }

    }

    override fun onStart() {
        super.onStart()
        auth = Firebase.auth
    }
}