package com.capstone.signlingo

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.capstone.signlingo.databinding.FragmentProfileBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Header
import retrofit2.Response
import android.util.Log
git init
data class UpdateUserRequest(
    val fullName: String,
    val email: String,
    val password: String
)

data class UpdateUserResponse(
    val message: String,
    val user: User
)

data class User(
    val id: String,
    val fullName: String,
    val email: String,
    val password: String,
    val createdAt: String,
    val updatedAt: String
)

interface ApiService {
    @PUT("users/{id}")
    suspend fun updateUser(
        @Path("id") id: String,
        @Header("Authorization") token: String,
        @Body updateUserRequest: UpdateUserRequest
    ): Response<UpdateUserResponse>
}

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var apiService: ApiService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = requireActivity().getSharedPreferences("SignLingoPrefs", Context.MODE_PRIVATE)
        val email = sharedPref.getString("userEmail", "")
        val username = sharedPref.getString("userName", "")
        val userId = sharedPref.getString("userId", "")
        val token = sharedPref.getString("token", "")

        binding.emailEditText.setText(email)
        binding.usernameEditText.setText(username)

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://signlingo-r2uff3666q-et.a.run.app/api/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)

        binding.newPasswordEditText.addTextChangedListener {
            binding.confirmButton.isEnabled = it.toString().isNotEmpty() &&
                    binding.repeatNewPasswordEditText.text.isNotEmpty()
        }

        binding.repeatNewPasswordEditText.addTextChangedListener {
            binding.confirmButton.isEnabled = it.toString().isNotEmpty() &&
                    binding.newPasswordEditText.text.isNotEmpty()
        }

        binding.confirmButton.setOnClickListener {
            val fullName = binding.usernameEditText.text.toString()
            val updatedEmail = binding.emailEditText.text.toString()
            val newPassword = binding.newPasswordEditText.text.toString()

            if (userId != null && token != null) {
                updateUser(userId, fullName, updatedEmail, newPassword, token)
            } else {
                Toast.makeText(requireContext(), "Failed to retrieve user data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateUser(id: String, fullName: String, email: String, password: String, token: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiService.updateUser(id, "Bearer $token", UpdateUserRequest(fullName, email, password))
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null && responseBody.message == "User updated successfully") {
                            Toast.makeText(requireContext(), responseBody.message, Toast.LENGTH_SHORT).show()
                            Log.d("ProfileFragment", "User updated: ${responseBody.user}")
                        } else {
                            Toast.makeText(requireContext(), "Failed to update profile", Toast.LENGTH_SHORT).show()
                            Log.d("ProfileFragment", "Response body is null or message is not 'User updated successfully'")
                        }
                    } else {
                        Toast.makeText(requireContext(), "Failed to update profile", Toast.LENGTH_SHORT).show()
                        Log.d("ProfileFragment", "Response is not successful: ${response.errorBody()?.string()}")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    Log.e("ProfileFragment", "Exception: ${e.message}", e)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
