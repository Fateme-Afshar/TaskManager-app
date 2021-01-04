package com.example.taskmaneger.viewModel;

import android.app.Application;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.Editable;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;

import com.example.taskmaneger.data.TaskRepository;
import com.example.taskmaneger.model.Task;
import com.example.taskmaneger.model.TaskState;
import com.example.taskmaneger.utils.ProgramUtils;
import com.example.taskmaneger.view.IOnClickListener;
import com.example.taskmaneger.view.fragment.AddTaskFragment;
import com.example.taskmaneger.view.fragment.DatePickerFragment;
import com.example.taskmaneger.view.fragment.TimePickerFragment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddTaskViewModel extends CommonPartAddAndUpdateTask {
    private Task mTask = new Task();
    private TaskState mTaskState;
    private long mUserId;

    private IOnClickListener mOnClickListener;

    public AddTaskViewModel(Application application) {
        super(application);
    }

    public void setTaskState(TaskState taskState) {
        mTaskState = taskState;
    }

    public void setTaskTime(Date date) {
        mTask.setTime(date);
    }

    public void setTaskDate(Date date) {
        mTask.setDate(date);
    }

    public void setUserId(long userId) {
        mUserId = userId;
    }

    public void setOnClickListener(IOnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    @Override
    public void onClickListener() {
        if (getPhotoFile()!=null) {
            mTask.setImgAddress(getPhotoFile().getAbsolutePath());
        }
        else{
            mTask.setImgAddress("");
        }

        mTask.setTaskState(mTaskState);
        mTask.setUserId(mUserId);
        Log.d(ProgramUtils.TAG,"Add new task in database");
        getRepository().insert(mTask);
        mOnClickListener.onButtonClickListener();
    }

    @Override
    public Task getTask() {
        return mTask;
    }
}
