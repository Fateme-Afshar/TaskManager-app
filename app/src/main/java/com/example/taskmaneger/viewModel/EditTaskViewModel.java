package com.example.taskmaneger.viewModel;

import android.text.Editable;

import com.example.taskmaneger.data.TaskRepository;
import com.example.taskmaneger.model.Task;
import com.example.taskmaneger.view.IOnClickListener;

import java.util.Date;

import javax.inject.Inject;

public class EditTaskViewModel extends CommonPartAddAndUpdateTask {
    private Task mTask;
    private IOnClickListener mOnClickListener;

    @Inject
    public EditTaskViewModel(TaskRepository taskRepository) {
        super(taskRepository);
    }

    @Override
    public void onClickListener() {
        getRepository().update(mTask);
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

    public void setOnClickListener(IOnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public void setTask(Task task) {
        mTask = task;
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
