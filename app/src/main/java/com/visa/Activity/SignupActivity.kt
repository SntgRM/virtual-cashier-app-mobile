package com.visa.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.text.InputType
import android.view.MotionEvent
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.visa.R

class SignupActivity : AppCompatActivity() {

    private lateinit var nameEditText : EditText
    private lateinit var phoneNumberEditText : EditText
    private lateinit var passwordEditText : EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var textLogin : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        nameEditText = findViewById(R.id.nameEditText)
        phoneNumberEditText = findViewById(R.id.phoneNumberEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText)
        textLogin = findViewById(R.id.textLogin)

        textLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        setPasswordToggle(passwordEditText)
        setPasswordToggle(confirmPasswordEditText)
    }

    private fun setPasswordToggle(editText: EditText) {
        editText.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD
        editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.eye_password_icon, 0)
        editText.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableRight = editText.compoundDrawables[2]
                val clickX = event.x.toInt()
                if (drawableRight != null && clickX >= (editText.width - editText.paddingRight - drawableRight.bounds.width())) {
                    if (editText.inputType == InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD) {
                        editText.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                        editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.eye_off_password_icon, 0) // Ícono de "ojo abierto"
                    } else {
                        editText.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD
                        editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.eye_password_icon, 0) // Ícono de "ojo cerrado"
                    }
                    editText.setSelection(editText.text.length)
                }
            }
            false
        }
    }


    fun onSignupButtonClick(view: View) {
        val phoneNumber = phoneNumberEditText.text.toString()
        val password = passwordEditText.text.toString()
        val confirmPassword = confirmPasswordEditText.text.toString()
        val name = nameEditText.text.toString()

        if (phoneNumber.length != 10) {
            Toast.makeText(this, "El número de teléfono debe tener exactamente 10 caracteres.", Toast.LENGTH_SHORT).show()
            return
        }

        if (password.length !=4) {
            Toast.makeText(this, "La contraseña debe tener exactamente 4 caracteres.", Toast.LENGTH_SHORT).show()
            return
        }

        if (password != confirmPassword) {
            Toast.makeText(this, "Las contraseñas no coinciden.", Toast.LENGTH_SHORT).show()
            return
        }

        val validPrefixes = listOf(
            "310", "311", "312", "313", "314", "321", "320", "322", "323", // Claro
            "315", "316", "317", "318", // Movistar
            "305", // ETB
            "319", // VirginMobile
            "350", "351", // Avantel
            "300", "301", "302", "304" // Tigo
        )

        val validPrefix = validPrefixes.any { phoneNumber.startsWith(it) }

        if (!validPrefix) {
            Toast.makeText(this, "Número no válido, intente de nuevo", Toast.LENGTH_SHORT).show()
            return
        }

        if (phoneNumber.isNotEmpty() && password.isNotEmpty()) {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("phone_number", phoneNumber)
            intent.putExtra("user_name", name)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "Por favor, ingresa todos los datos.", Toast.LENGTH_SHORT).show()
        }
    }
}
