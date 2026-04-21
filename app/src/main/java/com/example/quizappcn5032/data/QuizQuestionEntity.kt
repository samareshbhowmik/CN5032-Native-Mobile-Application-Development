package com.example.quizappcn5032.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quiz_questions")
data class QuizQuestionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val quizId: Int,
    val questionId: Int
)