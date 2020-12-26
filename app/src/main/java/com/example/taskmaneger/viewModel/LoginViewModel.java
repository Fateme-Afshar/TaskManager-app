package com.example.taskmaneger.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.taskmaneger.R;
import com.example.taskmaneger.data.UserRepository;
import com.example.taskmaneger.model.User;
import com.example.taskmaneger.utils.ViewUtils;
import com.example.taskmaneger.view.IOnClickListener;
import com.example.taskmaneger.view.activity.TaskManagerActivity;

public class LoginViewModel extends AndroidViewModel {
    private final UserRepository mRepository;
    private String mUsername;
    private String mPass;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        mRepository=UserRepository.getInstance(getApplication());
    }

    public void onTextChangedUsername(CharSequence username, int i, int j, int k) {
        mUsername=username.toString();
    }

    public void onTextChangedPassword(CharSequence password, int i, int j, int k) {
        mPass=password.toString();
    }

    public void onLoginClickListener(){
        if (mRepository.checkExistUser(mUsername)){
            User user=mRepository.get(mUsername);
            if (mPass.equals(user.getPassword())) {
                TaskManagerActivity.start(getApplication(), user.getId());
            }else
                ViewUtils.returnToast(getApplication(),R.string.invalid_information);
        }else
            ViewUtils.returnToast(getApplication(), R.string.user_dont_exist);
    }

}
