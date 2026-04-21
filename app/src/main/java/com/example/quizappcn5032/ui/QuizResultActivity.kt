package com.example.quizappcn5032.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.quizappcn5032.databinding.ActivityQuizResultBinding

class QuizResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val score = intent.getIntExtra("score", 0)
        val total = intent.getIntExtra("total", 0)

        binding.txtScore.text = "Score: $score/$total"

        val feedback = when {
            total == 0 -> "No questions attempted."
            score == total -> "Excellent! Perfect score."
            score >= total * 0.7 -> "Well done! Good performance."
            score >= total * 0.4 -> "Fair attempt. Keep practising."
            else -> "Needs improvement. Try again."
        }

        binding.txtFeedback.text = feedback
    }
}