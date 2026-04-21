package com.example.quizappcn5032.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.quizappcn5032.data.AppDatabase
import com.example.quizappcn5032.data.QuestionEntity
import com.example.quizappcn5032.databinding.ActivityAddQuestionBinding
import kotlinx.coroutines.launch

class AddQuestionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddQuestionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSaveQuestion.setOnClickListener {
            saveQuestion()
        }
    }

    private fun saveQuestion() {
        val questionText = binding.etQuestionText.text.toString().trim()
        val optionA = binding.etOptionA.text.toString().trim()
        val optionB = binding.etOptionB.text.toString().trim()
        val optionC = binding.etOptionC.text.toString().trim()
        val optionD = binding.etOptionD.text.toString().trim()
        val correctAnswer = binding.etCorrectAnswer.text.toString().trim()
        val difficulty = binding.etDifficulty.text.toString().trim()

        if (questionText.isEmpty() || optionA.isEmpty() || optionB.isEmpty() ||
            optionC.isEmpty() || optionD.isEmpty() || correctAnswer.isEmpty() || difficulty.isEmpty()
        ) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            val db = AppDatabase.getDatabase(this@AddQuestionActivity)
            db.questionDao().insertQuestion(
                QuestionEntity(
                    questionText = questionText,
                    optionA = optionA,
                    optionB = optionB,
                    optionC = optionC,
                    optionD = optionD,
                    correctAnswer = correctAnswer,
                    difficulty = difficulty
                )
            )
            Toast.makeText(this@AddQuestionActivity, "Question saved", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}