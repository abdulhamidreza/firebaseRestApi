package com.example.firebaserestapi.repository;

import android.os.Handler;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.firebaserestapi.data.User;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    MutableLiveData<List<User>> userListMutableLiveData;
    FirebaseDatabase mFirestore;
    MutableLiveData<User> userMutableLiveData;

    public UserRepository() {
        this.userListMutableLiveData = new MutableLiveData<>();
        //define firestore
        mFirestore = FirebaseDatabase.getInstance();
        //define userlist
        userMutableLiveData = new MutableLiveData<>();

    }

    //get user from firebase
    public MutableLiveData<List<User>> getUserDetailsListMutableLiveData() {
        Log.i("TAG", "getUserDetailsListMutableLiveData: ");
        List<User> userList = new ArrayList<>();
        User user = new User();
        user.setName("Hamid");
        user.setEmail("an@gmail.com");
        user.setPhone("888888333");
        userList.add(user);
        User user1 = new User();
        user1.setName("Hamid1");
        user1.setEmail("an@gmail.com1");
        user1.setPhone("8888883331");
        userList.add(user1);
        new Handler().postDelayed(() -> {
            userListMutableLiveData.postValue(userList);
        }, 3000);


        return userListMutableLiveData;
    }

    //delete user

    //update user
}