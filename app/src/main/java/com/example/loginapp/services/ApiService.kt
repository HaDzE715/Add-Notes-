package com.example.loginapp.services

import com.example.loginapp.LoginData
import com.example.loginapp.Note
import com.example.loginapp.NoteResponse
import com.example.loginapp.RegistrationData
import com.example.loginapp.UserApiResponse
import com.example.loginapp.updateData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("/signup")
    suspend fun registerUser(@Body registrationData: RegistrationData): Response<Void>

    @POST("/login")
    suspend fun loginUser(@Body loginData: LoginData): Response<UserApiResponse>

    @POST("/updateUser")
    suspend fun updateUser(@Body userData: updateData): Response<Void>

    @POST("/addNote")
    suspend fun addNote(@Body note: Note): Response<Void>

    @GET("/getNotes")
    suspend fun getNotes(@Query("username") username: String): Response<List<NoteResponse>>
}
