package com.example.firebaserestapi.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.firebaserestapi.R;
import com.example.firebaserestapi.data.User;
import com.example.firebaserestapi.viewmodel.SaveUserViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;


public class AddUserFragment extends Fragment {

    private View rootView;
    private SaveUserViewModel mViewModel;
    private TextInputEditText userNameETxt;
    private TextInputEditText userEmailETxt;
    private TextInputEditText userPhoneETxt;
    private AppCompatButton saveUserBtn;
    ContentLoadingProgressBar contentLoadingProgressBar;


    public static AddUserFragment newInstance() {
        return new AddUserFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_add_user, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        userNameETxt = rootView.findViewById(R.id.editTextName);
        userEmailETxt = rootView.findViewById(R.id.editTextEmail);
        userPhoneETxt = rootView.findViewById(R.id.editTextPhone);
        saveUserBtn = rootView.findViewById(R.id.buttonSave);
        contentLoadingProgressBar =
                (ContentLoadingProgressBar) rootView.findViewById(R.id.saveProgressBar);

        mViewModel = new ViewModelProvider(this).get(SaveUserViewModel.class);

        saveUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contentLoadingProgressBar.show();
                saveUserDetails();
            }
        });

    }

    private void saveUserDetails() {
        User user = new User();
        user.setName(Objects.requireNonNull(userNameETxt.getText()).toString());
        user.setEmail(Objects.requireNonNull(userEmailETxt.getText()).toString());
        user.setPhone(Objects.requireNonNull(userPhoneETxt.getText()).toString());

        mViewModel.saveUserLiveData(user).observe(this.requireActivity(), isSaved -> {
            Log.d("**************", isSaved + "");
            contentLoadingProgressBar.hide();
        });
    }


}