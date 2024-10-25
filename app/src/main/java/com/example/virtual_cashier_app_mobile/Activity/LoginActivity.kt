package com.example.virtual_cashier_app_mobile.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.virtual_cashier_app_mobile.R

class LoginActivity : AppCompatActivity() {

    private lateinit var phoneNumberEditText: EditText
    private lateinit var passwordEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        phoneNumberEditText = findViewById(R.id.phoneNumberEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
    }

    fun onLoginButtonClick(view: View) {
        val phoneNumber = phoneNumberEditText.text.toString()
        val password = passwordEditText.text.toString()


        if (phoneNumber.isNotEmpty() && password.isNotEmpty()) {

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "Por favor, ingresa todos los datos.", Toast.LENGTH_SHORT).show()
        }
    }
}
