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

public abstract  class CommonPartAddAndUpdateTask extends AndroidViewModel {
    public static final int REQUEST_CODE_DATE_PICKER = 1;
    public static final int REQUEST_CODE_TIME_PICKER = 2;
    public static final int REQUEST_CODE_TAKE_PICTURE = 3;
    public static final String AUTHORITY = "com.example.taskmanager.fileprovider";
    private File mPhotoFile;
    private Task mTask;
    private TaskRepository mRepository;

    public CommonPartAddAndUpdateTask(Application application) {
        super(application);
        mRepository=TaskRepository.getInstance(application);
        mTask=getTask();
    }

    public abstract void onClickListener();

    public void onDatePickerClickListener(Fragment fragment) {
        mTask=new Task();
        DatePickerFragment datePickerFragment =
                DatePickerFragment.newInstance(mTask.getDate());

        // create parent-child relationship between AddTaskFragment and DatePickerFragment
        datePickerFragment.setTargetFragment(fragment, REQUEST_CODE_DATE_PICKER);

        datePickerFragment.show
                (fragment.getParentFragmentManager(),
                        AddTaskFragment.TAG_ADD_TASK_FRAGMENT);
    }

    public void onTimePickerClickListener(Fragment fragment){
        mTask=new Task();
        TimePickerFragment timePickerFragment=
                TimePickerFragment.newInstance(mTask.getDate());

        // create parent-child relationship between AddTaskFragment and TimePickerFragment
        timePickerFragment.setTargetFragment(fragment, REQUEST_CODE_TIME_PICKER);

        timePickerFragment.show
                (fragment.getParentFragmentManager(),
                        AddTaskFragment.TAG_ADD_TASK_FRAGMENT);
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

    public File getPhotoFile() {
        return mPhotoFile;
    }

    public abstract Task getTask();

    public Uri getUriPhoto() {
        return FileProvider.getUriForFile(getApplication(), AUTHORITY, mPhotoFile);
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
}
