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
import com.example.firebaserestapi.data.UserKt;
import com.example.firebaserestapi.network.RetrofitServiceKt;
import com.example.firebaserestapi.repository.UserRepositoryKt;
import com.example.firebaserestapi.viewmodel.MyViewModelFactory;
import com.example.firebaserestapi.viewmodel.SaveUserViewModelKt;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;


public class AddUserFragment extends Fragment {

    private View rootView;
    private SaveUserViewModelKt mViewModel;
    private TextInputEditText userNameETxt;
    private TextInputEditText userEmailETxt;
    private TextInputEditText userPhoneETxt;
    private AppCompatButton saveUserBtn;
    private ContentLoadingProgressBar contentLoadingProgressBar;


    public static AddUserFragment newInstance() {
        return new AddUserFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RetrofitServiceKt retrofitService = RetrofitServiceKt.Companion.getInstance();
        UserRepositoryKt userRepositoryKt = new UserRepositoryKt(retrofitService);
        mViewModel = new ViewModelProvider(this, new MyViewModelFactory(userRepositoryKt)).get(SaveUserViewModelKt.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_add_user, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userNameETxt = rootView.findViewById(R.id.editTextName);
        userEmailETxt = rootView.findViewById(R.id.editTextEmail);
        userPhoneETxt = rootView.findViewById(R.id.editTextPhone);
        saveUserBtn = rootView.findViewById(R.id.buttonSave);
        contentLoadingProgressBar =
                (ContentLoadingProgressBar) rootView.findViewById(R.id.saveProgressBar);

        saveUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserDetails();
            }
        });

    }

    private void saveUserDetails() {

        mViewModel.getErrorMessage().observe(this.requireActivity(), it -> {
            Log.e("********** getErrorMessage", it);
        });

        mViewModel.getLoadingStatus().observe(this.requireActivity(), it -> {
            Log.e("********** getLoading", "is Loading " + it);
            if (it) {
                contentLoadingProgressBar.hide();
                requireActivity().onBackPressed();
            } else {
                contentLoadingProgressBar.hide();
                saveUserBtn.setTextColor(getResources().getColor(android.R.color.holo_orange_light));
            }
        });

        UserKt user = new UserKt(Objects.requireNonNull(userNameETxt.getText()).toString(),
                Objects.requireNonNull(userEmailETxt.getText()).toString(),
                Objects.requireNonNull(userPhoneETxt.getText()).toString());

        contentLoadingProgressBar.show();
        mViewModel.postUserData(user);
    }


}