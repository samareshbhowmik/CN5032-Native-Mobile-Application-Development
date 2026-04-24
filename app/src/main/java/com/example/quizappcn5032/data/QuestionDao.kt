package com.example.quizappcn5032.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface QuestionDao {

    @Insert
    suspend fun insertQuestion(question: QuestionEntity): Long

    @Query("SELECT * FROM questions ORDER BY id DESC")
    suspend fun getAllQuestions(): List<QuestionEntity>

    @Query("SELECT * FROM questions WHERE id IN (:questionIds)")
    suspend fun getQuestionsByIds(questionIds: List<Int>): List<QuestionEntity>

    @Update
    suspend fun updateQuestion(question: QuestionEntity)

    @Delete
    suspend fun deleteQuestion(question: QuestionEntity)
}
