package com.example.taskmaneger.di;

import android.content.Context;

import com.example.taskmaneger.di.module.ContextModule;
import com.example.taskmaneger.view.fragment.AddTaskFragment;
import com.example.taskmaneger.view.fragment.AdminFragment;
import com.example.taskmaneger.view.fragment.BottomSheetFragment;
import com.example.taskmaneger.view.fragment.DatePickerFragment;
import com.example.taskmaneger.view.fragment.EditTaskFragment;
import com.example.taskmaneger.view.fragment.LoginFragment;
import com.example.taskmaneger.view.fragment.SignUpFragment;
import com.example.taskmaneger.view.fragment.StateFragment;
import com.example.taskmaneger.view.fragment.TaskManagerFragment;
import com.example.taskmaneger.view.fragment.TimePickerFragment;

import dagger.Component;

@ApplicationScope
@Component(modules = ContextModule.class)
public interface ApplicationGraph {

    @ApplicationContext
    Context getContext();

    void inject(SignUpFragment signUpFragment);
    void inject(LoginFragment loginFragment);
    void inject(AddTaskFragment addTaskFragment);
    void inject(BottomSheetFragment bottomSheetFragment);
    void inject(DatePickerFragment datePickerFragment);
    void inject(EditTaskFragment editTaskFragment);
    void inject(StateFragment stateFragment);
    void inject(TaskManagerFragment taskManagerFragment);
    void inject(TimePickerFragment timePickerFragment);
    void inject(AdminFragment adminFragment);
}
