package com.example.taskmaneger.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.taskmaneger.model.User;

public class LoginViewModel extends AndroidViewModel {
    private User mUser;

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }
}
