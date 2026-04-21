
package com.example.quizappcn5032.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizappcn5032.data.AppDatabase
import com.example.quizappcn5032.data.QuizQuestionEntity
import com.example.quizappcn5032.databinding.ActivitySelectQuestionsForQuizBinding
import kotlinx.coroutines.launch

class SelectQuestionsForQuizActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelectQuestionsForQuizBinding
    private lateinit var adapter: SelectQuestionAdapter
    private var quizId: Int = 0
    private var quizTitle: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectQuestionsForQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        quizId = intent.getIntExtra("quizId", 0)
        quizTitle = intent.getStringExtra("quizTitle") ?: ""

        binding.txtQuizName.text = "Quiz: $quizTitle"
        binding.recyclerSelectQuestions.layoutManager = LinearLayoutManager(this)

        loadQuestions()

        binding.btnSaveSelectedQuestions.setOnClickListener {
            saveSelectedQuestions()
        }
    }

    private fun loadQuestions() {
        lifecycleScope.launch {
            val db = AppDatabase.getDatabase(this@SelectQuestionsForQuizActivity)
            val questions = db.questionDao().getAllQuestions()
            adapter = SelectQuestionAdapter(questions)
            binding.recyclerSelectQuestions.adapter = adapter
        }
    }

    private fun saveSelectedQuestions() {
        val selectedIds = adapter.getSelectedQuestionIds()

        if (selectedIds.isEmpty()) {
            Toast.makeText(this, "Please select at least one question", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            val db = AppDatabase.getDatabase(this@SelectQuestionsForQuizActivity)

            db.quizQuestionDao().deleteQuestionsForQuiz(quizId)

            for (questionId in selectedIds) {
                db.quizQuestionDao().insertQuizQuestion(
                    QuizQuestionEntity(
                        quizId = quizId,
                        questionId = questionId
                    )
                )
            }

            Toast.makeText(this@SelectQuestionsForQuizActivity, "Questions added to quiz", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}