package com.example.quizappcn5032.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.quizappcn5032.data.AppDatabase
import com.example.quizappcn5032.data.QuizEntity
import com.example.quizappcn5032.databinding.ActivityCreateQuizBinding
import kotlinx.coroutines.launch

class CreateQuizActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateQuizBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSaveQuiz.setOnClickListener {
            saveQuiz()
        }
    }

    private fun saveQuiz() {
        val title = binding.etQuizTitle.text.toString().trim()
        val difficulty = binding.etQuizDifficulty.text.toString().trim()
        val date = binding.etQuizDate.text.toString().trim()
        val time = binding.etQuizTime.text.toString().trim()
        val durationText = binding.etQuizDuration.text.toString().trim()

        if (title.isEmpty() || difficulty.isEmpty() || date.isEmpty() || time.isEmpty() || durationText.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val duration = durationText.toIntOrNull()
        if (duration == null) {
            Toast.makeText(this, "Enter valid duration", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            val db = AppDatabase.getDatabase(this@CreateQuizActivity)

            val quizId = db.quizDao().insertQuiz(
                QuizEntity(
                    title = title,
                    difficulty = difficulty,
                    scheduledDate = date,
                    scheduledTime = time,
                    durationMinutes = duration
                )
            ).toInt()

            Toast.makeText(this@CreateQuizActivity, "Quiz saved. Now select questions.", Toast.LENGTH_SHORT).show()

            val intent = Intent(this@CreateQuizActivity, SelectQuestionsForQuizActivity::class.java)
            intent.putExtra("quizId", quizId)
            intent.putExtra("quizTitle", title)
            startActivity(intent)
            finish()
        }
    }
}