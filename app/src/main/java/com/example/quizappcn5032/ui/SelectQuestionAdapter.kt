package com.example.quizappcn5032.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quizappcn5032.data.QuestionEntity
import com.example.quizappcn5032.databinding.ItemSelectQuestionBinding

class SelectQuestionAdapter(
    private val questionList: List<QuestionEntity>
) : RecyclerView.Adapter<SelectQuestionAdapter.SelectQuestionViewHolder>() {

    private val selectedQuestionIds = mutableSetOf<Int>()

    inner class SelectQuestionViewHolder(val binding: ItemSelectQuestionBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectQuestionViewHolder {
        val binding = ItemSelectQuestionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SelectQuestionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SelectQuestionViewHolder, position: Int) {
        val question = questionList[position]

        holder.binding.txtQuestionText.text = question.questionText
        holder.binding.txtQuestionDifficulty.text = "Difficulty: ${question.difficulty}"

        holder.binding.checkSelectQuestion.setOnCheckedChangeListener(null)
        holder.binding.checkSelectQuestion.isChecked = selectedQuestionIds.contains(question.id)

        holder.binding.checkSelectQuestion.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                selectedQuestionIds.add(question.id)
            } else {
                selectedQuestionIds.remove(question.id)
            }
        }
    }

    override fun getItemCount(): Int = questionList.size

    fun getSelectedQuestionIds(): List<Int> {
        return selectedQuestionIds.toList()
    }
}