package com.example.taskmaneger.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmaneger.R;
import com.example.taskmaneger.databinding.ItemAdminViewBinding;
import com.example.taskmaneger.model.User;

import java.util.List;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.Holder> {
    private List<User> mUserList;
    private Context mContext;

    private AdminAdapterCallback mCallbacks;

    public AdminAdapter(List<User> userList, Context context
            , AdminAdapterCallback callbacks) {
        mUserList = userList;
        mContext = context;
        mCallbacks=callbacks;
    }

    public List<User> getUserList() {
        return mUserList;
    }

    public void setUserList(List<User> userList) {
        mUserList = userList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        ItemAdminViewBinding binding=
                DataBindingUtil.inflate(
                        layoutInflater,
                        R.layout.item_admin_view,
                        parent,
                        false);

        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(mUserList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallbacks.onItemUserSelected(mUserList.get(position).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        private ItemAdminViewBinding mBinding;

        public Holder(@NonNull ItemAdminViewBinding binding) {
            super(binding.getRoot());
            mBinding=binding;
        }


        public void bind(User user) {
            mBinding.setUser(user);
            mBinding.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallbacks.onTrashCanClickListener(user);
                }
            });
        }
    }
    public interface AdminAdapterCallback{
        void onItemUserSelected(long userId);
        void onTrashCanClickListener(User user);
    }
}
