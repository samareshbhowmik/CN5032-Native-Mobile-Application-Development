package com.example.quizappcn5032.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.quizappcn5032.MainActivity
import com.example.quizappcn5032.databinding.ActivityAdminDashboardBinding

class AdminDashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAddQuestion.setOnClickListener {
            startActivity(Intent(this, AddQuestionActivity::class.java))
        }

        binding.btnViewQuestions.setOnClickListener {
            startActivity(Intent(this, QuestionListActivity::class.java))
        }

        binding.btnCreateQuiz.setOnClickListener {
            startActivity(Intent(this, CreateQuizActivity::class.java))
        }

        binding.btnSignOut.setOnClickListener {
            signOut()
        }
    }

    private fun signOut() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
    }
}
