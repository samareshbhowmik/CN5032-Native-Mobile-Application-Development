
package com.example.quizappcn5032.ui

import android.os.Bundle
import android.view.View
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
    private var adapter: SelectQuestionAdapter? = null
    private var quizId: Int = 0
    private var quizTitle: String = ""

    companion object {
        const val EXTRA_QUIZ_ID = "quizId"
        const val EXTRA_QUIZ_TITLE = "quizTitle"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectQuestionsForQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Toast.makeText(this, "SelectQuestionsForQuizActivity opened", Toast.LENGTH_SHORT).show()

        quizId = intent.getIntExtra(EXTRA_QUIZ_ID, 0)
        quizTitle = intent.getStringExtra(EXTRA_QUIZ_TITLE) ?: ""

        if (quizId <= 0) {
            Toast.makeText(this, "Quiz details missing. Please create the quiz again.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        binding.txtDebugScreenOpened.text = "SELECT QUESTIONS SCREEN OPENED"
        binding.txtQuizName.text = "Quiz: $quizTitle (ID: $quizId)"
        binding.txtQuestionLoadStatus.text = "Loading questions..."
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

            if (questions.isEmpty()) {
                binding.txtQuestionLoadStatus.text = "Loaded 0 questions"
                binding.txtNoQuestions.visibility = View.VISIBLE
                binding.recyclerSelectQuestions.visibility = View.GONE
                binding.btnSaveSelectedQuestions.isEnabled = false
            } else {
                binding.txtQuestionLoadStatus.text = "Loaded ${questions.size} question(s)"
                binding.txtNoQuestions.visibility = View.GONE
                binding.recyclerSelectQuestions.visibility = View.VISIBLE
                adapter = SelectQuestionAdapter(questions)
                binding.recyclerSelectQuestions.adapter = adapter
                binding.btnSaveSelectedQuestions.isEnabled = true
            }
        }
    }

    private fun saveSelectedQuestions() {
        val selectedIds = adapter?.getSelectedQuestionIds().orEmpty()

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
