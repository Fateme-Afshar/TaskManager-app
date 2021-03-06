package com.example.taskmaneger.viewModel;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.ShareCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.example.taskmaneger.R;
import com.example.taskmaneger.data.TaskRepository;
import com.example.taskmaneger.model.Task;
import com.example.taskmaneger.view.IOnClickListener;

import java.text.DateFormat;

public class BottomSheetViewModel extends AndroidViewModel {
    private TaskRepository mRepository;
    private long mTaskId;
    private Task mTask;

    private LifecycleOwner mLifecycleOwner;
    private IOnClickListener mOnClickListener;

    private BottomSheetFragmentViewModelCallback mCallback;

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

    public void setOnClickListener(IOnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public void onDeleteBtnClickListener(){
        mRepository.delete(mTask);
        mOnClickListener.onButtonClickListener();
    }

    public void onShareBtnClickListener(){
        mCallback.onShareTask(getInfoTask());
        mOnClickListener.onButtonClickListener();
    }

    public interface BottomSheetFragmentViewModelCallback{
        void onShareTask(String taskInfo);
    }

    public void setCallback(BottomSheetFragmentViewModelCallback callback) {
        mCallback = callback;
    }

    private String getInfoTask(){
        return getApplication().getString(R.string.all_task_info,
                mTask.getTitle(),
                mTask.getDescription(),
                mTask.getTaskState().toString(),
                DateFormat.getDateInstance(DateFormat.SHORT).
                        format(mTask.getDate()),
                DateFormat.getTimeInstance(DateFormat.SHORT).
                        format(mTask.getTime()));
    }
}
