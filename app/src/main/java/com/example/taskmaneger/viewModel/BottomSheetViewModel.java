package com.example.taskmaneger.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.example.taskmaneger.data.TaskRepository;
import com.example.taskmaneger.model.Task;

public class BottomSheetViewModel extends AndroidViewModel {
    private TaskRepository mRepository;
    private long mTaskId;
    private Task mTask;

    private LifecycleOwner mLifecycleOwner;

    public BottomSheetViewModel(@NonNull Application application) {
        super(application);
        mRepository=TaskRepository.getInstance(getApplication());
    }

    public Task getTask() {
        return mTask;
    }

    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        mLifecycleOwner = lifecycleOwner;
    }

    public void setTaskId(long taskId) {
        mTaskId = taskId;
        mRepository.get(mTaskId).observe(mLifecycleOwner, new Observer<Task>() {
            @Override
            public void onChanged(Task task) {
                mTask=task;
            }
        });
    }
}
