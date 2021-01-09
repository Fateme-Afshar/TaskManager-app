package com.example.taskmaneger.viewModel;

import android.Manifest;
import android.app.Application;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.taskmaneger.data.TaskRepository;
import com.example.taskmaneger.data.UserRepository;
import com.example.taskmaneger.data.UserWithTask;
import com.example.taskmaneger.model.Task;
import com.example.taskmaneger.view.IOnClickListener;

import java.util.List;

public class StateViewModel extends AndroidViewModel {
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 1;
    private TaskRepository mTaskRepository;

    private IOnClickListener mOnClickListener;

    private String PERMISSION="android.permission.CAMERA";

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

    public void deleteAllTask(long userId){
        mTaskRepository.deleteAll(userId);
    }
}
