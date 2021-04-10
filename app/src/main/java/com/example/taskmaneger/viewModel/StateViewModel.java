package com.example.taskmaneger.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.taskmaneger.data.TaskRepository;
import com.example.taskmaneger.model.Task;
import com.example.taskmaneger.view.IOnClickListener;

import java.util.List;

import javax.inject.Inject;

public class StateViewModel extends ViewModel {
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 1;
    private TaskRepository mTaskRepository;

    private IOnClickListener mOnClickListener;

    private String PERMISSION="android.permission.CAMERA";

    @Inject
    public StateViewModel(TaskRepository taskRepository) {
        mTaskRepository=taskRepository;
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
