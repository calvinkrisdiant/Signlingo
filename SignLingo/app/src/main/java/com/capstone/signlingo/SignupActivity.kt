package com.capstone.signlingo

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.capstone.signlingo.api.ApiService
import com.capstone.signlingo.api.RegisterRequest
import com.capstone.signlingo.databinding.ActivitySignupBinding

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://signlingo-r2uff3666q-et.a.run.app/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)

        // Add text change listeners to enable signup button when fields are not empty
        binding.username.addTextChangedListener(signupTextWatcher)
        binding.email.addTextChangedListener(signupTextWatcher)
        binding.password.addTextChangedListener(signupTextWatcher)

        // Show password functionality
        binding.showPassword.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.password.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                binding.password.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            binding.password.setSelection(binding.password.text.length) // Move cursor to the end
        }

        // Handle signup button click
        binding.signupButton.setOnClickListener {
            val fullName = binding.username.text.toString().trim()
            val email = binding.email.text.toString().trim()
            val password = binding.password.text.toString().trim()

            if (fullName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                signup(fullName, email, password)
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val signupTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val usernameInput = binding.username.text.toString().trim()
            val emailInput = binding.email.text.toString().trim()
            val passwordInput = binding.password.text.toString().trim()
            binding.signupButton.isEnabled = usernameInput.isNotEmpty() && emailInput.isNotEmpty() && passwordInput.isNotEmpty()
            binding.signupButton.setBackgroundResource(
                if (usernameInput.isNotEmpty() && emailInput.isNotEmpty() && passwordInput.isNotEmpty())
                    R.drawable.button_background_enabled
                else
                    R.drawable.button_background_disabled
            )
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    private fun signup(fullName: String, email: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiService.register(RegisterRequest(fullName, email, password))
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val registerResponse = response.body()
                        if (registerResponse != null) {
                            when (registerResponse.message) {
                                "Email already exists" -> {
                                    Toast.makeText(this@SignupActivity, "Email already exists", Toast.LENGTH_SHORT).show()
                                }
                                "Member baru berhasil ditambahkan." -> {
                                    Toast.makeText(this@SignupActivity, "Registration successful", Toast.LENGTH_SHORT).show()
                                    val intent = Intent(this@SignupActivity, MainActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                                "Panjang nama pengguna dan kata sandi harus setidaknya 8 karakter" -> {
                                    Toast.makeText(this@SignupActivity, "Username and password must be at least 8 characters long", Toast.LENGTH_SHORT).show()
                                }
                                else -> {
                                    Toast.makeText(this@SignupActivity, "Registration failed: ${registerResponse.message}", Toast.LENGTH_SHORT).show()
                                }
                            }
                        } else {
                            Toast.makeText(this@SignupActivity, "Registration failed: Unknown error", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        // Handle error response
                        val errorBody = response.errorBody()?.string()
                        val errorMessage = errorBody?.let {
                            try {
                                JSONObject(it).getString("message")
                            } catch (e: Exception) {
                                "Error: ${response.message()}"
                            }
                        } ?: "Error: ${response.message()}"
                        Toast.makeText(this@SignupActivity, errorMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: HttpException) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@SignupActivity, "Network error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: IOException) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@SignupActivity, "IO error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@SignupActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
