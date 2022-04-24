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
import com.example.firebaserestapi.data.UserAdapter;
import com.example.firebaserestapi.data.UserKt;
import com.example.firebaserestapi.network.RetrofitServiceKt;
import com.example.firebaserestapi.repository.UserRepositoryKt;
import com.example.firebaserestapi.viewmodel.MyViewModelFactory;
import com.example.firebaserestapi.viewmodel.UserListViewModelKt;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class UserListFragment extends Fragment implements UserAdapter.OnUserItemClickedListener {

    private UserListViewModelKt mViewModel;
    private RecyclerView userRecycler;
    private UserAdapter userAdapter;
    private View rootView;
    private ContentLoadingProgressBar contentLoadingProgressBar;
    private List<UserKt> userKtList = new ArrayList<>();

    public static UserListFragment newInstance() {
        return new UserListFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RetrofitServiceKt retrofitService = RetrofitServiceKt.Companion.getInstance();
        UserRepositoryKt userRepositoryKt = new UserRepositoryKt(retrofitService);
        mViewModel = new ViewModelProvider(this, new MyViewModelFactory(userRepositoryKt)).get(UserListViewModelKt.class);
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
        contentLoadingProgressBar.setVisibility(View.VISIBLE);

        mViewModel.getUserKtList().observe(this.requireActivity(), userListSet -> {
            userAdapter = new UserAdapter(userListSet, this);
            userKtList = userListSet;
            userRecycler.setAdapter(userAdapter);
            userAdapter.notifyDataSetChanged();
            Log.e("********** loadUserList", userListSet.toString());
        });

        mViewModel.getErrorMessage().observe(this.requireActivity(), it -> {
            Log.e("********** getErrorMessage", it);
        });

        mViewModel.getLoadingStatus().observe(this.requireActivity(), it -> {
            Log.e("********** getLoading", "is Loading " + it);
            if (it) {
                contentLoadingProgressBar.setVisibility(View.GONE);
            } else {
                contentLoadingProgressBar.setBackgroundColor(getResources().getColor(android.R.color.system_accent1_10));
            }
        });

        mViewModel.getAllUsers();

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
        UserKt userSelected = userKtList.get(position);

        Bundle bundle = new Bundle();
        bundle.putString("user", userSelected.getName());
        bundle.putString("email", userSelected.getEmail());
        bundle.putString("phone", userSelected.getPhone());

        String backStateName = this.getClass().getName();
        Fragment fragment = ShowUsersFragment.newInstance();
        fragment.setArguments(bundle);
        requireActivity().getSupportFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(backStateName)
                .replace(R.id.container, fragment)
                .commit();
    }

}
