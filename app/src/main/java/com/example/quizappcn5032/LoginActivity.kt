package com.example.quizappcn5032.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.quizappcn5032.data.AppDatabase
import com.example.quizappcn5032.databinding.ActivityLoginBinding
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLoginSubmit.setOnClickListener {
            loginUser()
        }
    }

    private fun loginUser() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            val db = AppDatabase.getDatabase(this@LoginActivity)
            val user = db.userDao().loginUser(email, password)

            if (user != null) {
                Toast.makeText(this@LoginActivity, "Login successful", Toast.LENGTH_SHORT).show()

                if (user.role == "admin") {
                    startActivity(Intent(this@LoginActivity, AdminDashboardActivity::class.java))
                } else {
                    startActivity(Intent(this@LoginActivity, StudentDashboardActivity::class.java))
                }

                finish()
            } else {
                Toast.makeText(this@LoginActivity, "Invalid email or password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}