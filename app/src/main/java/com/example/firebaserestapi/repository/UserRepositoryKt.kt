package com.example.firebaserestapi.repository

import com.example.firebaserestapi.network.RetrofitServiceKt

class UserRepositoryKt constructor(private val retrofitService: RetrofitServiceKt) {

    suspend fun getAllUsers() = retrofitService.getUserListData()

}