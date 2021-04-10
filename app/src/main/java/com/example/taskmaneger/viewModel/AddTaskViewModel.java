package com.example.taskmaneger.viewModel;

import android.text.Editable;
import android.util.Log;

import com.example.taskmaneger.data.TaskRepository;
import com.example.taskmaneger.model.Task;
import com.example.taskmaneger.model.TaskState;
import com.example.taskmaneger.utils.ProgramUtils;
import com.example.taskmaneger.view.IOnClickListener;

import java.util.Date;

import javax.inject.Inject;

public class AddTaskViewModel extends CommonPartAddAndUpdateTask {
    private Task mTask = new Task();
    private TaskState mTaskState;
    private long mUserId;

    private IOnClickListener mOnClickListener;

    @Inject
    public AddTaskViewModel(TaskRepository  taskRepository) {
        super(taskRepository);
    }

    public void setTaskState(TaskState taskState) {
        mTaskState = taskState;
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

    @Override
    public void setTaskDate(Date userSelectedDate) {
        mTask.setDate(userSelectedDate);
    }

    @Override
    public void setTaskTime(Date userSelectedTime) {
        mTask.setTime(userSelectedTime);
    }

    public void afterTextTitleChanged(Editable title) {
        if (!title.toString().equals(""))
            mTask.setTitle(title.toString());
        else
            mTask.setTitle("");
    }

    public void afterTextDescriptionChanged(Editable description) {
        if (!description.toString().equals(""))
            mTask.setDescription(description.toString());
        else
            mTask.setDescription("");
    }
}
