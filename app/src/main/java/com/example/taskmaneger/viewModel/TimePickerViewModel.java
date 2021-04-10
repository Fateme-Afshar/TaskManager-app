package com.example.taskmaneger.viewModel;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.example.taskmaneger.databinding.FragmentAddTaskBinding;

import java.util.Date;

import javax.inject.Inject;

public class TimePickerViewModel extends ViewModel {
    public static final String EXTRA_USER_SELECTED_TIME =
            "com.example.taskmaneger.userSelectedTime";

    @Inject
    public TimePickerViewModel() {

    }

    public void setResult(Fragment fragment, Date userSelectedTime){
        Fragment targetFragment=fragment.getTargetFragment();
        Intent data=new Intent();
        data.putExtra(EXTRA_USER_SELECTED_TIME,userSelectedTime);
        targetFragment.onActivityResult(fragment.getTargetRequestCode(),Activity.RESULT_OK,data);
    }
}
