package com.example.firebaserestapi.repository

import com.example.firebaserestapi.data.UserKt
import com.example.firebaserestapi.network.RetrofitServiceKt

class UserRepositoryKt constructor(private val retrofitService: RetrofitServiceKt) {

    suspend fun getAllUsers() = retrofitService.getUserListData()

    suspend fun postUserData(user: UserKt) = retrofitService.postUserData(user)

}