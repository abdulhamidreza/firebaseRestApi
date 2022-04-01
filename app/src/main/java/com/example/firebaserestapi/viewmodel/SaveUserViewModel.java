package com.example.firebaserestapi.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.firebaserestapi.data.User;
import com.example.firebaserestapi.repository.UserRepository;

public class SaveUserViewModel extends ViewModel {
    MutableLiveData<Boolean> saveUserMutableLiveData = new MutableLiveData<>();
    UserRepository userRepository;

    public SaveUserViewModel() {
        userRepository = new UserRepository();
    }

    public void saveUserLiveData(User user) {
        userRepository.saveUserDetailsMutableLiveData(user, saveUserMutableLiveData);
    }

    public LiveData<Boolean> getUserLiveData() {
        return saveUserMutableLiveData;
    }
}
