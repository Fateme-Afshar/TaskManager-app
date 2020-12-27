package com.example.taskmaneger.viewModel;

import android.app.Application;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;

import com.example.taskmaneger.data.TaskRepository;
import com.example.taskmaneger.model.Task;
import com.example.taskmaneger.view.fragment.AddTaskFragment;
import com.example.taskmaneger.view.fragment.DatePickerFragment;
import com.example.taskmaneger.view.fragment.TimePickerFragment;

import java.util.Date;

public class AddTaskViewModel extends AndroidViewModel {
    public static final int REQUEST_CODE_DATE_PICKER = 1;
    private TaskRepository mRepository;
    private Task mTask=new Task();

    public AddTaskViewModel(@NonNull Application application) {
        super(application);
    }

    public void onTextTitleChanged(CharSequence title, int i, int j, int k) {
        mTask.setTitle(title.toString());
    }

    public void onTextDescriptionChanged(CharSequence description, int i, int j, int k) {
        mTask.setTitle(description.toString());
    }

    public void onDatePickerClickListener(Fragment fragment){
        DatePickerFragment datePickerFragment=
                DatePickerFragment.newInstance(mTask.getDate());

        // create parent-child relationship between AddTaskFragment and DatePickerFragment
        datePickerFragment.setTargetFragment(fragment, REQUEST_CODE_DATE_PICKER);

        datePickerFragment.show
                (fragment.getParentFragmentManager(),
                        AddTaskFragment.TAG_ADD_TASK_FRAGMENT);
    }

    public void setTaskDate(Date date){
        mTask.setDate(date);
    }

    public Date getUserSelectedDate(Intent data){
        return (Date) data.getSerializableExtra(DatePickerViewModel.EXTRA_USER_SELECTED_DATE);
    }

    public Task getTask() {
        return mTask;
    }
}
