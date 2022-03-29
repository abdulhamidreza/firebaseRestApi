package com.example.firebaserestapi.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.firebaserestapi.data.User;
import com.example.firebaserestapi.repository.UserRepository;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class UserListViewModel extends ViewModel {
    MutableLiveData<List<User>> userListMutableLiveData;
    FirebaseDatabase mFirestore;
    UserRepository userRepository;

    public UserListViewModel() {
        userRepository = new UserRepository();
        userListMutableLiveData = userRepository.getUserDetailsListMutableLiveData();
        mFirestore = FirebaseDatabase.getInstance();
    }

    public MutableLiveData<List<User>> getLiveUserData() {
        return userListMutableLiveData;
    }
}
