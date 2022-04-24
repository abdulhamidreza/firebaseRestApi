package com.example.firebaserestapi.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.firebaserestapi.data.UserKt
import com.example.firebaserestapi.repository.UserRepositoryKt
import kotlinx.coroutines.*

class SaveUserViewModelKt constructor(private val mainRepository: UserRepositoryKt) : ViewModel() {
    val errorMessage = MutableLiveData<String>()
    val loadingStatus = MutableLiveData<Boolean>()

    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    var job: Job? = null


    fun postUserData(userKt: UserKt) {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = mainRepository.postUserData(userKt)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    loadingStatus.postValue(true)
                } else {
                    onError("Error : ${response.message()} ")
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
        Log.d("*************"," onCleared caled")
        job?.cancel()
    }
}