package com.example.quizappcn5032.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.RecyclerView
import com.example.quizappcn5032.data.AppDatabase
import com.example.quizappcn5032.data.QuestionEntity
import com.example.quizappcn5032.databinding.ItemQuestionBinding
import kotlinx.coroutines.launch

class QuestionAdapter(
    private val questionList: MutableList<QuestionEntity>,
    private val db: AppDatabase,
    private val lifecycleScope: LifecycleCoroutineScope
) : RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>() {

    inner class QuestionViewHolder(val binding: ItemQuestionBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val binding = ItemQuestionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return QuestionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val question = questionList[position]

        holder.binding.txtQuestionText.text = question.questionText
        holder.binding.txtOptions.text =
            "A: ${question.optionA}\nB: ${question.optionB}\nC: ${question.optionC}\nD: ${question.optionD}"
        holder.binding.txtDifficulty.text = "Difficulty: ${question.difficulty}"

        holder.binding.btnEditQuestion.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, EditQuestionActivity::class.java).apply {
                putExtra("id", question.id)
                putExtra("questionText", question.questionText)
                putExtra("optionA", question.optionA)
                putExtra("optionB", question.optionB)
                putExtra("optionC", question.optionC)
                putExtra("optionD", question.optionD)
                putExtra("correctAnswer", question.correctAnswer)
                putExtra("difficulty", question.difficulty)
            }
            context.startActivity(intent)
        }

        holder.binding.btnDeleteQuestion.setOnClickListener {
            lifecycleScope.launch {
                db.questionDao().deleteQuestion(question)
                questionList.removeAt(position)
                notifyItemRemoved(position)
                Toast.makeText(holder.itemView.context, "Question deleted", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int = questionList.size
}