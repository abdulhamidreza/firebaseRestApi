package com.example.firebaserestapi.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.firebaserestapi.data.UserKt
import com.example.firebaserestapi.repository.UserRepositoryKt
import com.google.gson.Gson
import kotlinx.coroutines.*

class UserListViewModelKt constructor(private val mainRepository: UserRepositoryKt) : ViewModel() {
    val errorMessage = MutableLiveData<String>()
    val userKtList = MutableLiveData<List<UserKt>>()
    val loadingStatus = MutableLiveData<Boolean>()

    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    var job: Job? = null


    fun getAllUsers() {
        Log.d("*************","getAllUsers Api hit")
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = mainRepository.getAllUsers()
            var users: MutableList<UserKt> = ArrayList<UserKt>()
            var isParsed = true
            var errorMassage = ""

            if (response.body() != null) {
                val gson = Gson();
                for ((key, value) in response.body()!!.entrySet()) {
                    try {
                        users.add(gson.fromJson(value, UserKt::class.java))
                    } catch (e: Exception) {
                        Log.e("Gson formatting Error with Key: $key  Value: $value", "", e)
                        isParsed = false
                        errorMassage =
                            "Error :" + " Gson formatting Error with Key: $key  Value: $value" + e.stackTraceToString()
                    }
                }

            } else {
                isParsed = false
                errorMassage = "Error : Body null "
            }

            withContext(Dispatchers.Main) {
                if (response.isSuccessful and isParsed) {
                    userKtList.postValue(users)
                    loadingStatus.postValue(true)
                } else {
                    onError("Error : ${response.message()} " + "Parse error " + errorMassage)
                }
            }
        }
    }

    private fun onError(message: String) {
        errorMessage.postValue(message)
        loadingStatus.postValue(false)
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}