package com.example.firebaserestapi.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.example.firebaserestapi.R;

public class ShowUsersFragment extends Fragment {

    private View rootView;
    private TextView mNameTxt;
    private TextView mEmailTxt;
    private TextView mPhoneTxt;
    private AppCompatButton mBackButton;

    public static ShowUsersFragment newInstance() {
        return new ShowUsersFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.show_users_fragment, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mNameTxt = rootView.findViewById(R.id.textView_alert_message_name);
        mEmailTxt = rootView.findViewById(R.id.textView_alert_message_email);
        mPhoneTxt = rootView.findViewById(R.id.textView_alert_message_phone);
        mBackButton = rootView.findViewById(R.id.button_go_to_home_screen);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mNameTxt.setText(bundle.getString("user"));
            mEmailTxt.setText(bundle.getString("email"));
            mPhoneTxt.setText(bundle.getString("phone"));
        }

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().onBackPressed();
            }
        });

    }


}