package com.example.firebaserestapi.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.firebaserestapi.repository.UserRepository;
import com.google.gson.JsonElement;

import java.util.Map;
import java.util.Set;

public class UserListViewModel extends ViewModel {
    LiveData<Set<Map.Entry<String, JsonElement>>> userListMutableLiveData;
    UserRepository userRepository;

    public UserListViewModel() {
        userRepository = new UserRepository();
    }

    public LiveData<Set<Map.Entry<String, JsonElement>>> getLiveUserData() {
        userListMutableLiveData = userRepository.getUserDetailsListMutableLiveData();
        return userListMutableLiveData;
    }
}
