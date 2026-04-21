package com.example.quizappcn5032.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quizappcn5032.data.QuizEntity
import com.example.quizappcn5032.databinding.ItemQuizBinding

class QuizListAdapter(
    private val quizList: List<QuizEntity>
) : RecyclerView.Adapter<QuizListAdapter.QuizViewHolder>() {

    inner class QuizViewHolder(val binding: ItemQuizBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        val binding = ItemQuizBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return QuizViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        val quiz = quizList[position]

        holder.binding.txtQuizTitle.text = quiz.title
        holder.binding.txtQuizDetails.text =
            "Difficulty: ${quiz.difficulty}\nDate: ${quiz.scheduledDate}\nTime: ${quiz.scheduledTime}\nDuration: ${quiz.durationMinutes} minutes"

        holder.binding.btnStartQuiz.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, QuizAttemptActivity::class.java).apply {
                putExtra("quizId", quiz.id)
                putExtra("quizTitle", quiz.title)
                putExtra("quizDuration", quiz.durationMinutes)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = quizList.size
}