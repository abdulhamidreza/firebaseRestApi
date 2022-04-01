package com.example.firebaserestapi.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebaserestapi.R;
import com.example.firebaserestapi.data.User;
import com.example.firebaserestapi.data.UserAdapter;
import com.example.firebaserestapi.viewmodel.UserListViewModel;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserListFragment extends Fragment implements UserAdapter.OnUserItemClickedListener {

    private UserListViewModel mViewModel;
    private RecyclerView userRecycler;
    private UserAdapter userAdapter;
    private View rootView;
    private ContentLoadingProgressBar contentLoadingProgressBar;
    private List<User> users = new ArrayList<>();
    private Gson gson = new Gson();

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(UserListViewModel.class);

        contentLoadingProgressBar = rootView.findViewById(R.id.loadProgressBar);

        ExtendedFloatingActionButton fab = (ExtendedFloatingActionButton) rootView.findViewById(R.id.add_user_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoAddUserFragment();
            }
        });
        userRecycler = rootView.findViewById(R.id.recycleView_container);
        userRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        userRecycler.setHasFixedSize(true);
        loadUserList();
    }
    
    private void loadUserList() {
        contentLoadingProgressBar.show();
        mViewModel.getLiveUserData().observe(this.requireActivity(), userListSet -> {
            for (Map.Entry<String, JsonElement> userList : userListSet) {
                try {
                    users.add(gson.fromJson(userList.getValue(), User.class));
                } catch (Exception e) {
                    Log.e("Gson formatting Error with Key: " + userList.getKey() + "  Value: " + userList.getValue(), "", e);
                }
            }
            userAdapter = new UserAdapter(users, this);
            userRecycler.setAdapter(userAdapter);
            userAdapter.notifyDataSetChanged();
            contentLoadingProgressBar.hide();
        });
    }

    private void gotoAddUserFragment() {
        String backStateName = this.getClass().getName();
        requireActivity().getSupportFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(backStateName)
                .replace(R.id.container, AddUserFragment.newInstance())
                .commit();
    }


    @Override
    public void onUserItemClicked(int position) {
        User userSelected = users.get(position);
        String backStateName = this.getClass().getName();

        Bundle bundle = new Bundle();
        bundle.putString("user", userSelected.getName());
        bundle.putString("email", userSelected.getEmail());
        bundle.putString("phone", userSelected.getPhone());
        Fragment fragment = ShowUsersFragment.newInstance();
        fragment.setArguments(bundle);
        requireActivity().getSupportFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(backStateName)
                .replace(R.id.container, fragment)
                .commit();
    }

}
