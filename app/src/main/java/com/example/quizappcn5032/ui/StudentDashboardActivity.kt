package com.example.quizappcn5032.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.quizappcn5032.MainActivity
import com.example.quizappcn5032.databinding.ActivityStudentDashboardBinding

class StudentDashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStudentDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnViewAvailableQuizzes.setOnClickListener {
            startActivity(Intent(this, AvailableQuizActivity::class.java))
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
