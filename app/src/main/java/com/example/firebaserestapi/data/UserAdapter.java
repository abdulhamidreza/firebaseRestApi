package com.example.firebaserestapi.data;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebaserestapi.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> implements AdapterView.OnItemClickListener {
    List<UserKt> mUserList;
    private OnUserItemClickedListener mOnUserItemClickedListener;

    public UserAdapter(List<UserKt> mUserList, OnUserItemClickedListener onUserItemClickedListener) {
        this.mUserList = mUserList;
        this.mOnUserItemClickedListener = onUserItemClickedListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_user_item, parent, false);
        return new ViewHolder(view, mOnUserItemClickedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(mUserList.get(position).getName());
        holder.email.setText(mUserList.get(position).getEmail());
        holder.phone.setText(mUserList.get(position).getPhone());

    }

    @Override
    public int getItemCount() {
        if (mUserList != null) {
            return mUserList.size();
        } else
            return 0;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        View view;
        TextView name;
        TextView email;
        TextView phone;
        OnUserItemClickedListener onUserItemClickedListener;

        public ViewHolder(@NonNull View itemView, OnUserItemClickedListener onUserItemClickedListener) {
            super(itemView);
            view = itemView;
            name = view.findViewById(R.id.name);
            email = view.findViewById(R.id.email);
            phone = view.findViewById(R.id.phone);
            this.onUserItemClickedListener = onUserItemClickedListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onUserItemClickedListener.onUserItemClicked(getAdapterPosition());
        }
    }

    public interface OnUserItemClickedListener{
        void onUserItemClicked(int position);
    }
}
