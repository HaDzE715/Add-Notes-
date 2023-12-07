package com.example.loginapp

import android.content.Intent
import android.util.Log
import com.example.loginapp.NetworkService.apiService
import com.example.loginapp.services.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkService {
    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    fun registerUser(
        registrationData: RegistrationData,
        onRegistrationComplete: (Boolean, String?) -> Unit
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = apiService.registerUser(registrationData)

                if (response.isSuccessful) {
                    onRegistrationComplete(true, "Registration successful")
                } else {
                    val errorMessage = response.message()
                    Log.e("Registration", "Registration failed with error: $errorMessage")
                    onRegistrationComplete(false, "Registration failed: $errorMessage")
                }
            } catch (e: Exception) {
                Log.e("Registration", "Network error: ${e.message}")
                onRegistrationComplete(false, "Network error: ${e.message}")
            }
        }
    }
}


fun performLogin(loginData: LoginData, onLoginComplete: (UserApiResponse) -> Unit) {
    // Create a LoginData object with the user's input
    // Launch a coroutine to make the network request
    GlobalScope.launch(Dispatchers.IO) {
        try {
            // Call the login method
            val response = apiService.loginUser(loginData)

            // Check if the login was successful
            if (response.isSuccessful)
            {
                val responseBody: UserApiResponse? = response.body()
                if (responseBody != null)
                {
                    Log.e("Login", "Login Success")
                    onLoginComplete(responseBody)
                }
            } else {
                // Handle login failure
                val errorMessage = response.message()
                Log.e("Login", "Login failed: $errorMessage")
            }
        } catch (e: Exception) {
            // Handle network or other exceptions
            Log.e("Login", "Network error: ${e.message}")
        }
    }
}

