package com.example.quizappcn5032.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizappcn5032.data.AppDatabase
import com.example.quizappcn5032.databinding.ActivityQuestionListBinding
import kotlinx.coroutines.launch

class QuestionListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuestionListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerQuestions.layoutManager = LinearLayoutManager(this)

        loadQuestions()
    }

    override fun onResume() {
        super.onResume()
        loadQuestions()
    }

    private fun loadQuestions() {
        lifecycleScope.launch {
            val db = AppDatabase.getDatabase(this@QuestionListActivity)
            val questions = db.questionDao().getAllQuestions().toMutableList()
            binding.recyclerQuestions.adapter = QuestionAdapter(questions, db, lifecycleScope)
        }
    }
}