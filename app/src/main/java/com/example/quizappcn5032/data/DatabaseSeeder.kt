package com.example.quizappcn5032.data

import android.content.Context

object DatabaseSeeder {

    suspend fun seedIfNeeded(context: Context) {
        val db = AppDatabase.getDatabase(context)

        seedUsers(db)
        val questionIds = seedQuestionsIfEmpty(db)
        seedQuizIfEmpty(db, questionIds)
    }

    private suspend fun seedUsers(db: AppDatabase) {
        val users = listOf(
            UserEntity(
                name = "Admin User",
                email = "admin@quizapp.com",
                password = "admin123",
                role = "admin"
            ),
            UserEntity(
                name = "Student User",
                email = "student@quizapp.com",
                password = "student123",
                role = "student"
            )
        )

        users.forEach { user ->
            if (db.userDao().getUserByEmail(user.email) == null) {
                db.userDao().insertUser(user)
            }
        }
    }

    private suspend fun seedQuestionsIfEmpty(db: AppDatabase): List<Int> {
        val existingQuestions = db.questionDao().getAllQuestions()
        if (existingQuestions.isNotEmpty()) {
            return existingQuestions.take(3).map { it.id }
        }

        val sampleQuestions = listOf(
            QuestionEntity(
                questionText = "What does CPU stand for?",
                optionA = "Central Processing Unit",
                optionB = "Computer Personal Unit",
                optionC = "Central Program Utility",
                optionD = "Control Processing User",
                correctAnswer = "A",
                difficulty = "Easy"
            ),
            QuestionEntity(
                questionText = "Which protocol is commonly used to load web pages?",
                optionA = "FTP",
                optionB = "HTTP",
                optionC = "SMTP",
                optionD = "SSH",
                correctAnswer = "B",
                difficulty = "Easy"
            ),
            QuestionEntity(
                questionText = "Which database library is used by this Android app?",
                optionA = "Firebase Realtime Database",
                optionB = "Realm",
                optionC = "Room",
                optionD = "MongoDB",
                correctAnswer = "C",
                difficulty = "Medium"
            ),
            QuestionEntity(
                questionText = "What is the purpose of a primary key in a database table?",
                optionA = "To store encrypted passwords",
                optionB = "To uniquely identify each row",
                optionC = "To format table text",
                optionD = "To delete duplicate columns",
                correctAnswer = "B",
                difficulty = "Medium"
            )
        )

        return sampleQuestions.map { question ->
            db.questionDao().insertQuestion(question).toInt()
        }
    }

    private suspend fun seedQuizIfEmpty(db: AppDatabase, questionIds: List<Int>) {
        if (db.quizDao().getAllQuizzes().isNotEmpty() || questionIds.isEmpty()) {
            return
        }

        val quizId = db.quizDao().insertQuiz(
            QuizEntity(
                title = "Computer Science Basics",
                difficulty = "Easy",
                scheduledDate = "2026-04-24",
                scheduledTime = "10:00",
                durationMinutes = 5
            )
        ).toInt()

        questionIds.take(3).forEach { questionId ->
            db.quizQuestionDao().insertQuizQuestion(
                QuizQuestionEntity(
                    quizId = quizId,
                    questionId = questionId
                )
            )
        }
    }
}
