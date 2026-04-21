package com.example.quizappcn5032.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quizzes")
data class QuizEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val difficulty: String,
    val scheduledDate: String,
    val scheduledTime: String,
    val durationMinutes: Int
)