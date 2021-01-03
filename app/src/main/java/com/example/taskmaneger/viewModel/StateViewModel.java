package com.example.taskmaneger.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.taskmaneger.data.TaskRepository;
import com.example.taskmaneger.data.UserRepository;
import com.example.taskmaneger.data.UserWithTask;
import com.example.taskmaneger.model.Task;
import com.example.taskmaneger.view.IOnClickListener;

import java.util.List;

public class StateViewModel extends AndroidViewModel {
    private TaskRepository mTaskRepository;

    private IOnClickListener mOnClickListener;

    public StateViewModel(@NonNull Application application) {
        super(application);
        mTaskRepository=TaskRepository.getInstance(getApplication());
    }

    public void setOnClickListener(IOnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public void onAddClickListener(){
        mOnClickListener.onButtonClickListener();
    }

    public LiveData<List<Task>> getTaskListWithUserId(long userId){
        return mTaskRepository.getListWithUserId(userId);
    }

    public LiveData<List<Task>> getTaskListWithState( String taskSate,long userId){
        return mTaskRepository.getListWithState(taskSate,userId);
    }
}
