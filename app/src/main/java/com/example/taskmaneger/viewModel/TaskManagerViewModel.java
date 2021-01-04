package com.example.taskmaneger.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.example.taskmaneger.data.UserRepository;
import com.example.taskmaneger.model.User;

public class TaskManagerViewModel extends AndroidViewModel {
    private final UserRepository mUserRepository;
    private User mUser;

    private LifecycleOwner mLifecycleOwner;

    public TaskManagerViewModel(@NonNull Application application) {
        super(application);
        mUserRepository=UserRepository.getInstance(getApplication());
    }

    public void setUserId(long userId) {
        mUserRepository.get(userId).observe(mLifecycleOwner, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                mUser=user;
            }
        });
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        mLifecycleOwner = lifecycleOwner;
    }
}
