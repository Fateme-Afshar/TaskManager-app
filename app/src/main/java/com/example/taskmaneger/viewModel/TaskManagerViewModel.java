package com.example.taskmaneger.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.taskmaneger.data.UserRepository;
import com.example.taskmaneger.model.User;

public class TaskManagerViewModel extends AndroidViewModel {
    private UserRepository mUserRepository;
    private User mUser;

    public TaskManagerViewModel(@NonNull Application application) {
        super(application);
        mUserRepository=UserRepository.getInstance(getApplication());
    }

    public void setUserId(long userId) {
        mUser=mUserRepository.get(userId);
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }
}
