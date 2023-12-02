package com.example.loginapp.services

import com.example.loginapp.LoginData
import com.example.loginapp.RegistrationData
import com.example.loginapp.UserApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/signup")
    suspend fun registerUser(@Body registrationData: RegistrationData): Response<Void>

    @POST("/login")
    suspend fun loginUser(@Body loginData: LoginData): Response<UserApiResponse>
}
