package com.example.taskmaneger.viewModel;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.taskmaneger.data.UserRepository;
import com.example.taskmaneger.model.User;

import javax.inject.Inject;

public class TaskManagerViewModel extends ViewModel {
    private final UserRepository mUserRepository;
    private User mUser;

    private LifecycleOwner mLifecycleOwner;

    @Inject
    public TaskManagerViewModel(UserRepository userRepository) {
        mUserRepository=userRepository;
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
