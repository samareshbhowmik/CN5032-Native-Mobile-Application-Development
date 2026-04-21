package com.example.quizappcn5032.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface QuizQuestionDao {

    @Insert
    suspend fun insertQuizQuestion(quizQuestion: QuizQuestionEntity)

    @Query("SELECT * FROM quiz_questions WHERE quizId = :quizId")
    suspend fun getQuestionsForQuiz(quizId: Int): List<QuizQuestionEntity>

    @Query("DELETE FROM quiz_questions WHERE quizId = :quizId")
    suspend fun deleteQuestionsForQuiz(quizId: Int)
}