package com.example.taskmaneger.viewModel;

import android.text.Editable;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.taskmaneger.R;
import com.example.taskmaneger.data.UserRepository;
import com.example.taskmaneger.model.User;
import com.example.taskmaneger.utils.ViewUtils;

import javax.inject.Inject;

public class LoginViewModel extends ViewModel {
    private final UserRepository mRepository;
    private String mUsername;
    private String mPass;

    private LifecycleOwner mLifecycleOwner;
    private LoginViewModelCallback mCallback;

    @Inject
    public LoginViewModel(UserRepository userRepository) {
        mRepository = userRepository;
    }

    public void afterTextChangedUsername(Editable username) {
        mUsername = username.toString();
    }

    public void afterTextChangedPassword(Editable password) {
        mPass=password.toString();
    }

    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        mLifecycleOwner = lifecycleOwner;
    }

    public void onLoginClickListener() {
        mRepository.get(mUsername).observe(mLifecycleOwner, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (user!=null){
                    mRepository.get(mUsername).observe(mLifecycleOwner, new Observer<User>() {
                        @Override
                        public void onChanged(User user) {
                            if (mPass.equals(user.getPassword())) {
                                if (!user.isAdmin()) {
                                    mCallback.startTaskManagerActivity(user.getId());
                                }else {
                                    mCallback.startAdminActivity();
                                }
                            } else
                                ViewUtils.returnToast( R.string.invalid_information);
                        }
                    });
                } else
                    ViewUtils.returnToast(R.string.user_dont_exist);
            }
        });
    }

    public LoginViewModelCallback getCallback() {
        return mCallback;
    }

    public void setCallback(LoginViewModelCallback callback) {
        mCallback = callback;
    }

    public interface LoginViewModelCallback{
        void startAdminActivity();
        void startTaskManagerActivity(long userId);
    }
}
