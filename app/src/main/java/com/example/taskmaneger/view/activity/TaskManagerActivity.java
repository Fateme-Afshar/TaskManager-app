package com.example.taskmaneger.view.activity;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.example.taskmaneger.R;
import com.example.taskmaneger.model.Task;
import com.example.taskmaneger.view.SingleFragmentActivity;
import com.example.taskmaneger.view.fragment.AddTaskFragment;
import com.example.taskmaneger.view.fragment.BottomSheetFragment;
import com.example.taskmaneger.view.fragment.StateFragment;
import com.example.taskmaneger.view.fragment.TaskManagerFragment;

public class TaskManagerActivity extends SingleFragmentActivity
        implements StateFragment.StateFragmentCallback ,
        AddTaskFragment.AddTaskFragmentCallback {

    public static final String EXTRA_USER_ID =
            "com.example.taskmaneger.User Id";
    public static final String STATE_FRAGMENT_TAG = "State Fragment";

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
                        STATE_FRAGMENT_TAG);

        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.fragment_container,
                        AddTaskFragment.newInstance(userId,taskState)).
                addToBackStack(STATE_FRAGMENT_TAG).
                commit();
    }

    @Override
    public void onMenuBtnSelectedListener(long taskId) {
        BottomSheetFragment bottomSheetFrag =
                BottomSheetFragment.newInstance(taskId);

        String tag = " Fragment Bottom Sheet";
        bottomSheetFrag.show(getSupportFragmentManager(), tag);
    }

    @Override
    public void onSaveBtnClickListener(long userId,String taskState) {
        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.fragment_container,
                        TaskManagerFragment.newInstance(userId)).
                addToBackStack(STATE_FRAGMENT_TAG).
                commit();
    }
}