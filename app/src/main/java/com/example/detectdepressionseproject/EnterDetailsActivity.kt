package com.example.detectdepressionseproject

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.detectdepressionseproject.databinding.ActivityEnterDetailsBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class EnterDetailsActivity : AppCompatActivity() {

    //firebase auth
    private lateinit var auth : FirebaseAuth

    //firebase user
    private lateinit var user : FirebaseUser

    //binding
    private lateinit var binding : ActivityEnterDetailsBinding

    //firestore
    private lateinit var db : FirebaseFirestore

    //arraylist of questions
    var questions = ArrayList<String>()

    //collection name
    private var PATIENT_DETAIL_COLLECTION = "PatientDetails"

    //patient email
    lateinit var patientEmail : String

    //details
    lateinit var patientName : String
    lateinit var patientAge : String
    lateinit var patientPhoneNumber : String
    lateinit var date : String
    lateinit var patientGender : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        FirebaseApp.initializeApp(this);
        binding = DataBindingUtil.setContentView(this@EnterDetailsActivity,R.layout.activity_enter_details)

        binding.progressBar.visibility = View.INVISIBLE

    }

    fun btnNextClicked(view : View) {
        binding.apply {
            progressBar.visibility = View.VISIBLE
            patientName = edtName.text.toString().trim()
            patientAge = edtAge.text.toString().trim()
            patientPhoneNumber = edtPhoneNumber.text.toString().trim()


            //deal with it
            if (TextUtils.isEmpty(patientName)) {
                edtName.setBackgroundColor(resources.getColor(R.color.purple_200))
            } else {
                edtName.background = null
            }
            if (TextUtils.isEmpty(patientAge)) {
                edtAge.setBackgroundColor(resources.getColor(R.color.purple_200))
            } else {
                edtAge.background = null
            }
            if (TextUtils.isEmpty(patientPhoneNumber)) {
                edtPhoneNumber.setBackgroundColor(resources.getColor(R.color.purple_200))
            } else {
                edtPhoneNumber.background = null
            }
            if (radioGroupGender.checkedRadioButtonId != -1) {
                patientGender = when(radioGroupGender.checkedRadioButtonId) {
                    R.id.rbMale -> "Male"
                    R.id.rbFemale -> "Female"
                    else -> "Other"
                }
            } else {
                rbFemale.setBackgroundColor(resources.getColor(R.color.purple_200))
                rbMale.setBackgroundColor(resources.getColor(R.color.purple_200))
                rbOther.setBackgroundColor(resources.getColor(R.color.purple_200))
            }

            if (!(TextUtils.isEmpty(patientName) || TextUtils.isEmpty(patientAge) ||  TextUtils.isEmpty(patientPhoneNumber) || TextUtils.isEmpty(patientEmail))) {

                val details = hashMapOf(
                    "Name" to patientName,
                    "Age" to patientAge,
                    "Gender" to patientGender,
                    "Phone Number" to patientPhoneNumber,
                    "Date" to FieldValue.serverTimestamp(),
                    "Score" to 0,
                    "Result" to "No"
                )

                db.collection(PATIENT_DETAIL_COLLECTION).document(patientEmail).set(details).addOnSuccessListener {
                    val intent = Intent(this@EnterDetailsActivity,MainActivity::class.java)
                    startActivity(intent)
                    finish()

                }.addOnFailureListener {
                    Toast.makeText(this@EnterDetailsActivity,"Ooops!! Something went wrong...", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    override fun onStart() {
        super.onStart()
        auth = Firebase.auth
        user = auth.currentUser!!
        patientEmail = user.email.toString()
    }
}