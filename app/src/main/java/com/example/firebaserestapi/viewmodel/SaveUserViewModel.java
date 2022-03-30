package com.example.firebaserestapi.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.firebaserestapi.data.User;
import com.example.firebaserestapi.repository.UserRepository;

public class SaveUserViewModel extends ViewModel {
    MutableLiveData<Boolean> saveUserMutableLiveData;
    UserRepository userRepository;

    public SaveUserViewModel() {
        userRepository = new UserRepository();
    }

    public MutableLiveData<Boolean> saveUserLiveData(User user) {
        saveUserMutableLiveData = userRepository.saveUserDetailsMutableLiveData(user);
        return saveUserMutableLiveData;
    }
}
