package com.example.taskmaneger.view.activity;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.example.taskmaneger.R;
import com.example.taskmaneger.view.SingleFragmentActivity;
import com.example.taskmaneger.view.fragment.AddTaskFragment;
import com.example.taskmaneger.view.fragment.SignUpFragment;
import com.example.taskmaneger.view.fragment.StateFragment;
import com.example.taskmaneger.view.fragment.TaskManagerFragment;

public class TaskManagerActivity extends SingleFragmentActivity
        implements StateFragment.StateFragmentCallback {

    public static final String EXTRA_USER_ID =
            "com.example.taskmaneger.User Id";
    public static final String ADD_TASK_FRAGMENT_TAG = "Add Task Fragment";

    public static void start(Context context, long userId) {
        Intent starter = new Intent(context, TaskManagerActivity.class);
        starter.putExtra(EXTRA_USER_ID,userId);
        starter.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(starter);
    }

    @Override
    public Fragment getFragment() {
        Intent intent=getIntent();
        long userId=intent.getLongExtra(EXTRA_USER_ID,0);
        return TaskManagerFragment.newInstance(userId);
    }

    @Override
    public void onAddBtnClickListener(long userId, String taskState) {
        getSupportFragmentManager().
                beginTransaction().
                add(StateFragment.newInstance(taskState,userId),
                        ADD_TASK_FRAGMENT_TAG);

        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.fragment_container,
                        AddTaskFragment.newInstance(userId,taskState)).
                addToBackStack(ADD_TASK_FRAGMENT_TAG).
                commit();
    }
}