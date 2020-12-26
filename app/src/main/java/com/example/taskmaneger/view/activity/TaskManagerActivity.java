package com.example.taskmaneger.view.activity;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.example.taskmaneger.view.SingleFragmentActivity;
import com.example.taskmaneger.view.fragment.TaskManagerFragment;

public class TaskManagerActivity extends SingleFragmentActivity {

    public static final String EXTRA_USER_ID =
            "com.example.taskmaneger.User Id";

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
}