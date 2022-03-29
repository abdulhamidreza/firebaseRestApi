package com.example.firebaserestapi.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebaserestapi.R;
import com.example.firebaserestapi.data.User;
import com.example.firebaserestapi.data.UserAdapter;
import com.example.firebaserestapi.viewmodel.UserListViewModel;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserListFragment extends Fragment {

    private UserListViewModel mViewModel;
    private RecyclerView userRecycler;
    private UserAdapter userAdapter;
    private View rootView;

    public static UserListFragment newInstance() {
        return new UserListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.user_list_fragment, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(UserListViewModel.class);

        userRecycler = rootView.findViewById(R.id.container);
        userRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        userRecycler.setHasFixedSize(true);
        // TODO: Use the ViewModel
        mViewModel.getLiveUserData().observe(this.requireActivity(), userListSet -> {
            List<User> users = new ArrayList<>();
            Gson gson = new Gson();
            for (Map.Entry<String, JsonElement> userList : userListSet
            ) {
                try {
                    users.add(gson.fromJson(userList.getValue(), User.class));
                } catch (Exception e) {
                    Log.e("Gson formatting Error with Key: " + userList.getKey() + "  Value: " + userList.getValue(), "", e);
                }
            }

            userAdapter = new UserAdapter(users);
            userRecycler.setAdapter(userAdapter);
            userAdapter.notifyDataSetChanged();


        });
    }

}