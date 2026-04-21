package com.example.quizappcn5032.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface QuizDao {

    @Insert
    suspend fun insertQuiz(quiz: QuizEntity): Long

    @Query("SELECT * FROM quizzes ORDER BY id DESC")
    suspend fun getAllQuizzes(): List<QuizEntity>
}