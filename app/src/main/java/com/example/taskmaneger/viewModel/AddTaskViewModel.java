package com.example.taskmaneger.viewModel;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;

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

public class AddTaskViewModel extends AndroidViewModel {
    public static final int REQUEST_CODE_DATE_PICKER = 1;
    public static final int REQUEST_CODE_TIME_PICKER = 2;
    public static final int REQUEST_CODE_TAKE_PICTURE = 3;
    public static final String AUTHORITY = "com.example.taskmanager.fileprovider";
    private TaskRepository mRepository;
    private Task mTask = new Task();
    private TaskState mTaskState;
    private long mUserId;
    private File mPhotoFile;

    public AddTaskViewModel(@NonNull Application application) {
        super(application);
        mRepository = TaskRepository.getInstance(getApplication());
    }

    public void onTextTitleChanged(CharSequence title, int i, int j, int k) {
        if (!title.toString().equals(""))
            mTask.setTitle(title.toString());
        else
            mTask.setTitle("");
    }

    public void onTextDescriptionChanged(CharSequence description, int i, int j, int k) {
        if (!description.toString().equals(""))
            mTask.setDescription(description.toString());
        else
            mTask.setDescription("");
    }

    public void onSaveBtnClickListener() {
        if (mPhotoFile.getAbsolutePath().equals("")) {
            mTask.setImgAddress(mPhotoFile.getAbsolutePath());
        }
        else{
            mTask.setImgAddress("dsa");
        }

        mTask.setTaskState(mTaskState);
        mTask.setUserId(mUserId);
        mRepository.insert(mTask);
    }

    public void onCameraClickListener(Fragment fragment) {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePicture.resolveActivity(getApplication().getPackageManager()) != null) {
            mPhotoFile = null;
            try {
                mPhotoFile = createImageFile();
            } catch (IOException e) {
                Log.e(ProgramUtils.TAG,
                        "Taking photo part: Error occurred while creating the File : " + e.getMessage());
            }
        }
        if (mPhotoFile != null) {
            Uri photoUri = FileProvider.getUriForFile(getApplication(),
                    AUTHORITY, mPhotoFile);
            takePicture.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            fragment.startActivityForResult(takePicture, REQUEST_CODE_TAKE_PICTURE);
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss").
                        format(new Date());
        String imageName = "JPEG" + timeStamp + "_";
        File storageDir = getApplication().getFilesDir();
        return File.createTempFile(imageName,
                ".jpg",
                storageDir);
    }

    public void onDatePickerClickListener(Fragment fragment) {
        DatePickerFragment datePickerFragment =
                DatePickerFragment.newInstance(mTask.getDate());

        // create parent-child relationship between AddTaskFragment and DatePickerFragment
        datePickerFragment.setTargetFragment(fragment, REQUEST_CODE_DATE_PICKER);

        datePickerFragment.show
                (fragment.getParentFragmentManager(),
                        AddTaskFragment.TAG_ADD_TASK_FRAGMENT);
    }

    public void onTimePickerClickListener(Fragment fragment){
        TimePickerFragment timePickerFragment=
                TimePickerFragment.newInstance(mTask.getDate());

        // create parent-child relationship between AddTaskFragment and TimePickerFragment
        timePickerFragment.setTargetFragment(fragment, REQUEST_CODE_TIME_PICKER);

        timePickerFragment.show
                (fragment.getParentFragmentManager(),
                        AddTaskFragment.TAG_ADD_TASK_FRAGMENT);
    }

    public void setTaskDate(Date date){
        mTask.setDate(date);
    }

    public Date getUserSelectedDate(Intent data){
        return (Date) data.getSerializableExtra(DatePickerViewModel.EXTRA_USER_SELECTED_DATE);
    }

    public void setTaskTime(Date date) {
        mTask.setTime(date);
    }

    public Date getUserSelectedTime(Intent data) {
        return (Date) data.getSerializableExtra(TimePickerViewModel.EXTRA_USER_SELECTED_TIME);
    }

    public Uri getUriPhoto() {
        return FileProvider.getUriForFile(getApplication(), AUTHORITY, mPhotoFile);
    }

    public Task getTask() {
        return mTask;
    }

    public void setTaskState(TaskState taskState) {
        mTaskState = taskState;
    }

    public void setUserId(long userId) {
        mUserId = userId;
    }

    public File getPhotoFile() {
        return mPhotoFile;
    }
}
