package com.example.quizappcn5032

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.quizappcn5032.data.AppDatabase
import com.example.quizappcn5032.data.UserEntity
import com.example.quizappcn5032.databinding.ActivityRegisterBinding
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val roles = listOf("student", "admin")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, roles)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerRole.adapter = adapter

        binding.btnRegisterSubmit.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        val name = binding.etName.text.toString().trim()
        val email = binding.etRegisterEmail.text.toString().trim()
        val password = binding.etRegisterPassword.text.toString().trim()
        val selectedRole = binding.spinnerRole.selectedItem.toString()

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            val db = AppDatabase.getDatabase(this@RegisterActivity)
            val existingUser = db.userDao().getUserByEmail(email)

            if (existingUser != null) {
                Toast.makeText(this@RegisterActivity, "Email already registered", Toast.LENGTH_SHORT).show()
            } else {
                val user = UserEntity(
                    name = name,
                    email = email,
                    password = password,
                    role = selectedRole
                )
                db.userDao().insertUser(user)
                Toast.makeText(this@RegisterActivity, "Registration successful", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}