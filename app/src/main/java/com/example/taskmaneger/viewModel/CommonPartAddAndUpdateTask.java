package com.example.taskmaneger.viewModel;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.Editable;
import android.util.Log;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.example.taskmaneger.TaskManagerApplication;
import com.example.taskmaneger.data.TaskRepository;
import com.example.taskmaneger.model.Task;
import com.example.taskmaneger.model.TaskState;
import com.example.taskmaneger.utils.ProgramUtils;
import com.example.taskmaneger.view.fragment.AddTaskFragment;
import com.example.taskmaneger.view.fragment.DatePickerFragment;
import com.example.taskmaneger.view.fragment.TimePickerFragment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract  class CommonPartAddAndUpdateTask extends ViewModel {
    public static final int REQUEST_CODE_DATE_PICKER = 1;
    public static final int REQUEST_CODE_TIME_PICKER = 2;
    public static final int REQUEST_CODE_TAKE_PICTURE = 3;
    public static final String AUTHORITY = "com.example.taskmanager.fileprovider";
    private File mPhotoFile;
    private Task mTask;
    private TaskRepository mRepository;

    private CommonPartCallback mCallback;

    public CommonPartAddAndUpdateTask(TaskRepository taskRepository) {
        mRepository=taskRepository;
        mTask=getTask();
    }

    public abstract void onClickListener();

    public void onDatePickerClickListener() {
        mCallback.onDatePickerClickListener();
    }

    public void onTimePickerClickListener(){
        mCallback.onTimePickerClickListener();
    }

    public void onCameraClickListener() {
        mCallback.onCameraClickListener();
    }

    public File createImageFile(Application application) throws IOException {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss").
                        format(new Date());
        String imageName = "JPEG" + timeStamp + "_";
        File storageDir = application.getFilesDir();
        return File.createTempFile(imageName,
                ".jpg",
                storageDir);
    }

    public File getPhotoFile() {
        return mPhotoFile;
    }

    public abstract Task getTask();

    public Uri getUriPhoto(Application application) {
        return FileProvider.getUriForFile(application, AUTHORITY, mPhotoFile);
    }

    public Date getUserSelectedTime(Intent data) {
        return (Date) data.getSerializableExtra(TimePickerViewModel.EXTRA_USER_SELECTED_TIME);
    }

    public Date getUserSelectedDate(Intent data){
        return (Date) data.getSerializableExtra(DatePickerViewModel.EXTRA_USER_SELECTED_DATE);
    }

    public TaskRepository getRepository() {
        return mRepository;
    }

    public abstract void setTaskDate(Date userSelectedDate);

    public abstract void setTaskTime(Date userSelectedTime);

    public void setCallback(CommonPartCallback callback) {
        mCallback = callback;
    }

    public void setPhotoFile(File photoFile) {
        mPhotoFile = photoFile;
    }

    public interface CommonPartCallback{
       void onCameraClickListener();
       void onTimePickerClickListener();
       void onDatePickerClickListener();
    }
}
