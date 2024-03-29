package com.example.detectdepressionseproject.questions

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.detectdepressionseproject.R
import com.example.detectdepressionseproject.databinding.ActivitySubjectiveQuestionsBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.database.FirebaseDatabase

class SubjectiveQuestionsActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySubjectiveQuestionsBinding

    private lateinit var submissionID : String

    private var QUESTION_NUMBER = 1

    private var SUBMISSIONS = "Submissions"

    private var SUBJECTIVE_QUESTIONS = "SubjectiveQuestions"

    private var questions = ArrayList<String>()

    private var TOTAL_QUESTIONS = 0

    private var score = 0

    private lateinit var database : FirebaseDatabase

    private lateinit var scoreArray : ArrayList<Int>

    private var SCORE_ARRAY = "Score Array"

    private lateinit var patientId : String

    private lateinit var docName : String

    private val PATIENT_ID = "PatientId"

    private var DOC_NAME = "DoctorName"

    private var SCORE = "Score"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this);
        patientId = intent.getStringExtra(PATIENT_ID)!!
        submissionID = patientId
        score = intent.getIntExtra(SCORE,0)
        scoreArray = intent.getIntegerArrayListExtra(SCORE_ARRAY)!!

        binding = DataBindingUtil.setContentView(this@SubjectiveQuestionsActivity,
            R.layout.activity_subjective_questions
        )
        database = FirebaseDatabase.getInstance()

        if (questions.size == 0) {
            loadQuestions()
        } else {
            setQuestion()
        }

        binding.btnPrevious.setOnClickListener {
            btnPrevious()
        }

        binding.btnNext.setOnClickListener {

            btnNext()
        }
    }

    private fun loadQuestions() {
        binding.apply {
            progressBar2.visibility = View.VISIBLE
            constraintLayout.visibility = View.INVISIBLE
            database.getReference(SUBJECTIVE_QUESTIONS).get().addOnSuccessListener {
                for (question in it.children) {
                    questions.add(question.value.toString())
                    Log.d("TAGY",question.value.toString())
                    TOTAL_QUESTIONS++
                }
                progressBar2.visibility = View.INVISIBLE
                constraintLayout.visibility = View.VISIBLE
                setQuestion()
            }.addOnFailureListener {
                Toast.makeText(this@SubjectiveQuestionsActivity,"Oops! Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setQuestion() {
        if (QUESTION_NUMBER in 1..TOTAL_QUESTIONS) {
            binding.txtQuestion.text = questions[QUESTION_NUMBER - 1].toString()
            binding.apply {
                if (QUESTION_NUMBER == 1) {
                    btnPrevious.visibility = View.GONE
                } else {
                    btnPrevious.visibility = View.VISIBLE
                    btnPrevious.text = "Previous"
                }
                if (QUESTION_NUMBER == TOTAL_QUESTIONS) {
                    btnNext.text = "Submit"
                } else {
                    btnNext.text = "Next"
                }
            }
        }
    }


    private fun btnPrevious() {
        if (QUESTION_NUMBER > 1) {
            QUESTION_NUMBER--
            setQuestion()
        }
    }

    fun btnNext() {

        if (QUESTION_NUMBER == TOTAL_QUESTIONS) {
            saveData()
        } else {
            QUESTION_NUMBER++
            setQuestion()
            binding.edtAns.setText("")
        }

    }

    private fun saveData() {
        TODO("Not yet implemented")
    }

    private fun generateSubmissionID() : String {
        var submissionIDs = getAllSubmissionIDs()
        return String.format("%04d",submissionIDs.size+1)
    }

    private fun getAllSubmissionIDs() : ArrayList<String> {
        var submissionIDs = ArrayList<String>()
        database.getReference(SUBMISSIONS).get().addOnSuccessListener {
            for (id in it.children) {
                if (id.key.toString() == patientId) {
                    for (subID in id.children) {
                        submissionIDs.add(subID.key.toString())
                    }
                    break
                }
            }
        }.addOnFailureListener {
            Toast.makeText(this@SubjectiveQuestionsActivity,"Oops! Something went wrong", Toast.LENGTH_SHORT).show()
        }
        return submissionIDs
    }



}