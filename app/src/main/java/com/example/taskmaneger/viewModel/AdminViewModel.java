package com.example.taskmaneger.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.taskmaneger.data.UserRepository;
import com.example.taskmaneger.model.User;

import java.util.List;

import javax.inject.Inject;

public class AdminViewModel extends ViewModel {
    private final UserRepository mUserRepository;

    @Inject
    public AdminViewModel(UserRepository userRepository) {
        mUserRepository=userRepository;
    }

    public LiveData<List<User>> getUserListLiveData(){
        return mUserRepository.getList();
    }

    public void deleteUser(User user){
        mUserRepository.delete(user);
    }

    public void deleteAll(){
        mUserRepository.deleteAll();
    }
}
