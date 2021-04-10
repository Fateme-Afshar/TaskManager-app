package com.example.taskmaneger.viewModel;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import java.util.Date;

import javax.inject.Inject;

public class DatePickerViewModel extends ViewModel {
    public static final String EXTRA_USER_SELECTED_DATE =
            " com.example.taskmaneger.userSelectedDate";

    @Inject
    public DatePickerViewModel() {

    }

    public void setResult(Fragment fragment, Date userSelectedDate) {
        Fragment targetFragment = fragment.getTargetFragment();
        Intent intent = new Intent();
        intent.putExtra(EXTRA_USER_SELECTED_DATE, userSelectedDate);
        targetFragment.onActivityResult(fragment.getTargetRequestCode(), Activity.RESULT_OK, intent);
    }
}
