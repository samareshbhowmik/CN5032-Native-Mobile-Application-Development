package com.example.quizappcn5032.ui

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.quizappcn5032.data.AppDatabase
import com.example.quizappcn5032.data.QuestionEntity
import com.example.quizappcn5032.databinding.ActivityQuizAttemptBinding
import kotlinx.coroutines.launch

class QuizAttemptActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizAttemptBinding

    private var quizId: Int = 0
    private var quizTitle: String = ""
    private var quizDuration: Int = 1

    private var questionList: List<QuestionEntity> = emptyList()
    private var currentIndex = 0

    private val selectedAnswers = mutableMapOf<Int, String>()

    private var timer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizAttemptBinding.inflate(layoutInflater)
        setContentView(binding.root)

        quizId = intent.getIntExtra("quizId", 0)
        quizTitle = intent.getStringExtra("quizTitle") ?: "Quiz"
        quizDuration = intent.getIntExtra("quizDuration", 1)

        binding.txtQuizAttemptTitle.text = quizTitle

        binding.btnPrevious.setOnClickListener {
            saveCurrentAnswer()
            if (currentIndex > 0) {
                currentIndex--
                showQuestion()
            }
        }

        binding.btnNext.setOnClickListener {
            saveCurrentAnswer()
            if (currentIndex < questionList.size - 1) {
                currentIndex++
                showQuestion()
            }
        }

        binding.btnSubmitQuiz.setOnClickListener {
            saveCurrentAnswer()
            submitQuiz()
        }

        loadQuizQuestions()
        startTimer(quizDuration)
    }

    private fun loadQuizQuestions() {
        lifecycleScope.launch {
            val db = AppDatabase.getDatabase(this@QuizAttemptActivity)

            val quizQuestionLinks = db.quizQuestionDao().getQuestionsForQuiz(quizId)
            val questionIds = quizQuestionLinks.map { it.questionId }

            if (questionIds.isEmpty()) {
                Toast.makeText(this@QuizAttemptActivity, "No questions found for this quiz", Toast.LENGTH_SHORT).show()
                finish()
                return@launch
            }

            questionList = db.questionDao().getQuestionsByIds(questionIds)

            if (questionList.isEmpty()) {
                Toast.makeText(this@QuizAttemptActivity, "Question data missing", Toast.LENGTH_SHORT).show()
                finish()
                return@launch
            }

            currentIndex = 0
            showQuestion()
        }
    }

    private fun showQuestion() {
        if (questionList.isEmpty()) return

        val question = questionList[currentIndex]

        binding.txtQuestionCounter.text = "Question ${currentIndex + 1}/${questionList.size}"
        binding.txtQuestionText.text = question.questionText

        binding.rbOptionA.text = "A. ${question.optionA}"
        binding.rbOptionB.text = "B. ${question.optionB}"
        binding.rbOptionC.text = "C. ${question.optionC}"
        binding.rbOptionD.text = "D. ${question.optionD}"

        binding.radioGroupOptions.clearCheck()

        when (selectedAnswers[question.id]) {
            "A" -> binding.rbOptionA.isChecked = true
            "B" -> binding.rbOptionB.isChecked = true
            "C" -> binding.rbOptionC.isChecked = true
            "D" -> binding.rbOptionD.isChecked = true
        }

        binding.btnPrevious.isEnabled = currentIndex > 0
        binding.btnNext.isEnabled = currentIndex < questionList.size - 1
    }

    private fun saveCurrentAnswer() {
        if (questionList.isEmpty()) return

        val question = questionList[currentIndex]

        val selected = when (binding.radioGroupOptions.checkedRadioButtonId) {
            binding.rbOptionA.id -> "A"
            binding.rbOptionB.id -> "B"
            binding.rbOptionC.id -> "C"
            binding.rbOptionD.id -> "D"
            else -> ""
        }

        if (selected.isNotEmpty()) {
            selectedAnswers[question.id] = selected
        }
    }

    private fun startTimer(durationMinutes: Int) {
        val totalMillis = durationMinutes * 60 * 1000L

        timer = object : CountDownTimer(totalMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = (millisUntilFinished / 1000) / 60
                val seconds = (millisUntilFinished / 1000) % 60
                binding.txtTimer.text = String.format("Time Left: %02d:%02d", minutes, seconds)
            }

            override fun onFinish() {
                binding.txtTimer.text = "Time Left: 00:00"
                saveCurrentAnswer()
                Toast.makeText(this@QuizAttemptActivity, "Time is up! Quiz submitted automatically.", Toast.LENGTH_SHORT).show()
                submitQuiz()
            }
        }.start()
    }

    private fun submitQuiz() {
        timer?.cancel()

        var score = 0
        for (question in questionList) {
            val selected = selectedAnswers[question.id]
            if (selected == question.correctAnswer) {
                score++
            }
        }

        val intent = Intent(this, QuizResultActivity::class.java)
        intent.putExtra("score", score)
        intent.putExtra("total", questionList.size)
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }
}