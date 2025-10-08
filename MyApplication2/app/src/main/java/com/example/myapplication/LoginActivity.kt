package com.example.myapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.EditText
import android.widget.Button
import android.widget.Toast
import android.app.DatePickerDialog
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.content.Intent
import android.graphics.Paint
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import android.widget.Switch
import android.widget.CheckBox
import androidx.core.content.edit

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val loginBtn = findViewById<Button>(R.id.loginbtn)
        val loginSwitch = findViewById<Switch>(R.id.loginSwitch)
        val emailOrUserField = findViewById<EditText>(R.id.login_email)
        val passField = findViewById<EditText>(R.id.login_password)
        val login_showPassword = findViewById<CheckBox>(R.id.login_showpass)

        login_showPassword.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                passField.transformationMethod = HideReturnsTransformationMethod.getInstance()
            } else {
                passField.transformationMethod = PasswordTransformationMethod.getInstance()
            }
            passField.setSelection(passField.text.length)
        }

        loginSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                emailOrUserField.hint = "Enter Username"
                loginSwitch.text = "Login with Username"
            } else {
                emailOrUserField.hint = "Enter Email"
                loginSwitch.text = "Login with Email"
            }
        }

        loginBtn.setOnClickListener {
            val input = emailOrUserField.text.toString().trim()
            val inputPass = passField.text.toString()

            val sharedPref = getSharedPreferences("UserPrefs", MODE_PRIVATE)
            val savedEmail = sharedPref.getString("email", null)
            val savedUser = sharedPref.getString("username", null)
            val savedPass = sharedPref.getString("password", null)

            val isUsingUsername = loginSwitch.isChecked

            if (input.isEmpty() || inputPass.isEmpty()) {
                Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show()
            } else {
                val loginSuccess =
                    if (isUsingUsername) {
                        input == savedUser && inputPass == savedPass
                    } else {
                        input == savedEmail && inputPass == savedPass
                    }

                if (loginSuccess) {
                    Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()

                    sharedPref.edit { putBoolean("isLoggedIn", true) }

                    val intent = Intent(this, LoadingActivity::class.java)
                    intent.putExtra("nextActivity", "Home")
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Invalid credentials!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        val signupLink = findViewById<TextView>(R.id.signup_link)

        signupLink.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}