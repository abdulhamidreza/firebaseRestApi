package com.example.firebaserestapi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.firebaserestapi.data.UserKt
import com.example.firebaserestapi.repository.UserRepositoryKt
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
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = mainRepository.getAllUsers()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    userKtList.postValue(response.body())
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
        job?.cancel()
    }
}