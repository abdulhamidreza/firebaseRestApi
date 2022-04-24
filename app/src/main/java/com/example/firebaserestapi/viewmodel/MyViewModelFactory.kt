package com.example.firebaserestapi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.firebaserestapi.repository.UserRepositoryKt


class MyViewModelFactory constructor(private val repository: UserRepositoryKt) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(UserListViewModelKt::class.java)) {
            UserListViewModelKt(this.repository) as T
        } else if (modelClass.isAssignableFrom(SaveUserViewModelKt::class.java)) {
            SaveUserViewModelKt(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}