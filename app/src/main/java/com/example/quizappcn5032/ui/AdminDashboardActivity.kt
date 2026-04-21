package com.example.quizappcn5032.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
    }
}