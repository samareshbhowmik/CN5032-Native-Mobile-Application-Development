# QuizAppCN5032

QuizAppCN5032 is a coursework Android quiz application built in Kotlin. The app supports basic user registration and login, separates users into admin and student roles, allows admins to manage a question bank and create quizzes, and allows students to attempt available quizzes with a countdown timer and result screen.

The project is intentionally simple and readable for coursework submission. It uses local device storage through Room rather than a remote server.

## App Purpose

The purpose of this app is to demonstrate a working mobile quiz system with:

- Role-based navigation for admins and students.
- Local user registration and login.
- Question bank management.
- Quiz creation and question selection.
- Student quiz attempts.
- Timed quiz sessions.
- Score calculation and feedback.

This matches the coursework aim of building a functional Android application that includes multiple screens, persistent storage, user interaction, and a complete application flow.

## Features

- Register as a student or admin.
- Login with stored local user details.
- Admin dashboard for quiz management.
- Add, view, edit, and delete questions.
- Create quizzes with title, difficulty, date, time, and duration.
- Select questions from the question bank for a quiz.
- Student dashboard for viewing available quizzes.
- Attempt quizzes one question at a time.
- Navigate between previous and next questions.
- Countdown timer during quiz attempts.
- Automatic submission when time runs out.
- Result screen showing score and feedback.
- Sample demo data for easier testing and demonstration.

## Tech Stack

- Language: Kotlin
- Platform: Android
- UI: XML layouts
- View access: ViewBinding
- Local database: Room
- Async work: Kotlin coroutines with `lifecycleScope`
- Lists: RecyclerView
- Build system: Gradle Kotlin DSL
- Minimum SDK: 24
- Target SDK: 36

## Project Structure

```text
QuizAppCN5032/
├── app/
│   ├── build.gradle.kts
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── java/com/example/quizappcn5032/
│       │   ├── MainActivity.kt
│       │   ├── LoginActivity.kt
│       │   ├── RegisterActivity.kt
│       │   ├── data/
│       │   │   ├── AppDatabase.kt
│       │   │   ├── DatabaseSeeder.kt
│       │   │   ├── UserEntity.kt
│       │   │   ├── UserDao.kt
│       │   │   ├── QuestionEntity.kt
│       │   │   ├── QuestionDao.kt
│       │   │   ├── QuizEntity.kt
│       │   │   ├── QuizDao.kt
│       │   │   ├── QuizQuestionEntity.kt
│       │   │   └── QuizQuestionDao.kt
│       │   └── ui/
│       │       ├── AdminDashboardActivity.kt
│       │       ├── StudentDashboardActivity.kt
│       │       ├── AddQuestionActivity.kt
│       │       ├── QuestionListActivity.kt
│       │       ├── EditQuestionActivity.kt
│       │       ├── CreateQuizActivity.kt
│       │       ├── SelectQuestionsForQuizActivity.kt
│       │       ├── AvailableQuizActivity.kt
│       │       ├── QuizAttemptActivity.kt
│       │       ├── QuizResultActivity.kt
│       │       ├── QuestionAdapter.kt
│       │       ├── SelectQuestionAdapter.kt
│       │       └── QuizListAdapter.kt
│       └── res/layout/
│           └── Activity and item XML layout files
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
```

## Database Design

The app uses Room with a local SQLite database named `quiz_app_db`.

### `users`

Stores registered users.

| Field | Purpose |
| --- | --- |
| `id` | Auto-generated primary key |
| `name` | User full name |
| `email` | Login email |
| `password` | Login password |
| `role` | Either `admin` or `student` |

### `questions`

Stores the reusable question bank.

| Field | Purpose |
| --- | --- |
| `id` | Auto-generated primary key |
| `questionText` | Question text |
| `optionA` | First answer option |
| `optionB` | Second answer option |
| `optionC` | Third answer option |
| `optionD` | Fourth answer option |
| `correctAnswer` | Correct option letter, such as `A` |
| `difficulty` | Question difficulty |

### `quizzes`

Stores quiz details.

| Field | Purpose |
| --- | --- |
| `id` | Auto-generated primary key |
| `title` | Quiz title |
| `difficulty` | Quiz difficulty |
| `scheduledDate` | Scheduled date as text |
| `scheduledTime` | Scheduled time as text |
| `durationMinutes` | Quiz duration in minutes |

### `quiz_questions`

Links quizzes to selected questions.

| Field | Purpose |
| --- | --- |
| `id` | Auto-generated primary key |
| `quizId` | Related quiz ID |
| `questionId` | Related question ID |

## App Flow

1. The app starts at `MainActivity`.
2. The user chooses Login or Register.
3. A new user registers with a role of `student` or `admin`.
4. Login checks the local Room database.
5. Admin users open the admin dashboard.
6. Student users open the student dashboard.

## Coursework Functionality

### User Registration and Login

Users register with name, email, password, and role. Registration checks whether the email already exists before inserting the user. Login checks the entered email and password against the local `users` table.

### Admin and Student Roles

The app uses the stored `role` field to decide which dashboard to show after login:

- `admin`: opens the admin dashboard.
- `student`: opens the student dashboard.

### Question Bank Management

Admins can add questions to the question bank. They can also view all questions, edit existing questions, and delete questions. Questions include four options, a correct answer, and a difficulty value.

### Quiz Creation

Admins create quizzes by entering the quiz title, difficulty, scheduled date, scheduled time, and duration. The quiz is saved in the `quizzes` table.

### Question Selection for Quiz

After creating a quiz, the admin selects questions from the question bank. Selected questions are saved in the `quiz_questions` table so each quiz can have its own set of linked questions.

### Quiz Attempt

Students can view available quizzes and start a quiz. The app loads all linked questions for the selected quiz and displays one question at a time with four answer options.

### Timer

Quiz attempts include a countdown timer based on the quiz duration. The timer updates every second. When time reaches zero, the quiz is submitted automatically.

### Result Screen

After submission, the app compares the selected answers with the stored correct answers. The result screen shows the final score and simple feedback based on performance.

## Sample Demo Data

The app includes a small seed-data helper for demonstration. When the app starts, it inserts demo data if needed.

Demo users:

| Role | Email | Password |
| --- | --- | --- |
| Admin | `admin@quizapp.com` | `admin123` |
| Student | `student@quizapp.com` | `student123` |

Demo content:

- Four sample computer science questions.
- One quiz named `Computer Science Basics`.
- The quiz is linked to sample questions so it can be attempted immediately.

The seeding is designed for coursework demonstration. It does not remove existing users, questions, or quizzes.

## Testing

The following main flows were tested manually during development:

- Register a new student account.
- Register a new admin account.
- Login as an admin and open the admin dashboard.
- Login as a student and open the student dashboard.
- Add a new question.
- View questions in the question bank.
- Edit an existing question.
- Delete a question.
- Create a quiz.
- Select questions for a quiz.
- View available quizzes as a student.
- Start a quiz attempt.
- Move between quiz questions using previous and next.
- Select answers and submit a quiz.
- Allow the timer to submit a quiz automatically.
- View final score and feedback on the result screen.
- Use seeded admin and student demo accounts for quick demonstration.

## Known Limitations

- Passwords are stored as plain text because this is a simple coursework prototype.
- There is no remote backend, so all data is stored on the local device only.
- There is no full session management or logout feature.
- Quiz dates and times are stored as plain text and are not enforced by scheduling logic.
- Students can currently see all quizzes in the local database.
- Quiz attempts and historical scores are not saved after the result screen.
- There is no password reset or email verification.
- Input validation is basic.
- The app uses destructive Room migration during development, so schema changes can clear local data.
- The UI is functional and simple rather than production polished.

## How to Run

1. Open the project in Android Studio.
2. Let Gradle sync.
3. Run the `app` configuration on an emulator or Android device.
4. Use the demo accounts above or create a new account from the registration screen.

## Notes for Submission

This project demonstrates the required coursework areas: Android screens, navigation, Room database persistence, user roles, CRUD-style question management, quiz creation, linked quiz questions, timed attempts, and result calculation. The implementation is kept straightforward so the code is easy to explain, test, and demonstrate.
