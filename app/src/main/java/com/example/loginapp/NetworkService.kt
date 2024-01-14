  package com.example.loginapp

import android.util.Log
import com.example.loginapp.NetworkService.apiService
import com.example.loginapp.services.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
object NetworkService {
    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("http://10.0.3.2:5000") // Change to 10.0.3.2 when using GennyMotion Emulator
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

fun addNote(note: Note, onNoteComplete: (Boolean, String?) -> Unit) {
    GlobalScope.launch(Dispatchers.IO) {
        try {
            val response = apiService.addNote(note)

            if (response.isSuccessful) {
                onNoteComplete(true, "Note sent successfully")
            } else {
                val errorMessage = response.message()
                onNoteComplete(false, "Note sending failed!: $errorMessage")
            }
        } catch (e: Exception) {
            onNoteComplete(false, "Network error: ${e.message}")
        }
    }
}
fun updateUserDetails(userData: updateData, onUpdateComplete: (Boolean, String?) -> Unit) {
    GlobalScope.launch(Dispatchers.IO) {
        try {
            val response = apiService.updateUser(userData)

            if (response.isSuccessful) {
                onUpdateComplete(true, "User details updated successfully")
            } else {
                val errorMessage = response.message()
                Log.e("UpdateUser", "User update failed with error: $errorMessage")
                onUpdateComplete(false, "User update failed: $errorMessage")
            }
        } catch (e: Exception) {
            Log.e("UpdateUser", "Network error: ${e.message}")
            onUpdateComplete(false, "Network error: ${e.message}")
        }
    }
}
  fun fetchNotes(username: String, onNotesFetched: (List<Pair<Int, String>>?, String?) -> Unit) {
      GlobalScope.launch(Dispatchers.IO) {
          try {
              val response = apiService.getNotes(username)

              if (response.isSuccessful) {
                  val notesResponseList: List<NoteResponse>? = response.body()

                  if (notesResponseList != null && notesResponseList.isNotEmpty()) {
                      // Extract all notes and id from the list
                      val notesWithId: List<Pair<Int, String>> = notesResponseList.map { it.id to it.note }

                      onNotesFetched(notesWithId, null)
                  } else {
                      onNotesFetched(null, "Empty or invalid response body")
                  }
              } else {
                  val errorMessage = response.message()
                  onNotesFetched(null, "Failed to fetch notes: $errorMessage")
              }
          } catch (e: Exception) {
              Log.e("FetchNotes", "Exception: ${e.message}")
              onNotesFetched(null, "Network error: ${e.message}")
          }
      }
  }









