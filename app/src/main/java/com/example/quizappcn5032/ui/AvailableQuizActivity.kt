package com.example.quizappcn5032.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizappcn5032.data.AppDatabase
import com.example.quizappcn5032.databinding.ActivityAvailableQuizBinding
import kotlinx.coroutines.launch

class AvailableQuizActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAvailableQuizBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAvailableQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerAvailableQuizzes.layoutManager = LinearLayoutManager(this)

        loadQuizzes()
    }

    private fun loadQuizzes() {
        lifecycleScope.launch {
            val db = AppDatabase.getDatabase(this@AvailableQuizActivity)
            val quizzes = db.quizDao().getAllQuizzes()
            binding.recyclerAvailableQuizzes.adapter = QuizListAdapter(quizzes)
        }
    }
}