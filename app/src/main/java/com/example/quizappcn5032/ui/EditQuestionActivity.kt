package com.example.quizappcn5032.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.quizappcn5032.data.AppDatabase
import com.example.quizappcn5032.data.QuestionEntity
import com.example.quizappcn5032.databinding.ActivityEditQuestionBinding
import kotlinx.coroutines.launch

class EditQuestionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditQuestionBinding
    private var questionId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        questionId = intent.getIntExtra("id", 0)

        binding.etQuestionText.setText(intent.getStringExtra("questionText"))
        binding.etOptionA.setText(intent.getStringExtra("optionA"))
        binding.etOptionB.setText(intent.getStringExtra("optionB"))
        binding.etOptionC.setText(intent.getStringExtra("optionC"))
        binding.etOptionD.setText(intent.getStringExtra("optionD"))
        binding.etCorrectAnswer.setText(intent.getStringExtra("correctAnswer"))
        binding.etDifficulty.setText(intent.getStringExtra("difficulty"))

        binding.btnUpdateQuestion.setOnClickListener {
            updateQuestion()
        }
    }

    private fun updateQuestion() {
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
            val db = AppDatabase.getDatabase(this@EditQuestionActivity)
            val updatedQuestion = QuestionEntity(
                id = questionId,
                questionText = questionText,
                optionA = optionA,
                optionB = optionB,
                optionC = optionC,
                optionD = optionD,
                correctAnswer = correctAnswer,
                difficulty = difficulty
            )
            db.questionDao().updateQuestion(updatedQuestion)
            Toast.makeText(this@EditQuestionActivity, "Question updated", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}