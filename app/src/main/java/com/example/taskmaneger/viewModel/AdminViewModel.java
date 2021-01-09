package com.example.taskmaneger.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.taskmaneger.data.UserRepository;
import com.example.taskmaneger.model.User;

import java.util.List;

public class AdminViewModel extends AndroidViewModel {
    private UserRepository mUserRepository;

    public AdminViewModel(@NonNull Application application) {
        super(application);
        mUserRepository= UserRepository.getInstance(application);
    }

    public LiveData<List<User>> getUserListLiveData(){
        return mUserRepository.getList();
    }

    public void deleteUser(User user){
        mUserRepository.delete(user);
    }
}
