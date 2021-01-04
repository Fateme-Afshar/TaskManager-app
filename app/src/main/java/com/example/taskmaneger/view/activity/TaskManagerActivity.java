package com.example.taskmaneger.view.activity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.example.taskmaneger.R;
import com.example.taskmaneger.utils.ProgramUtils;
import com.example.taskmaneger.view.SingleFragmentActivity;
import com.example.taskmaneger.view.fragment.AddTaskFragment;
import com.example.taskmaneger.view.fragment.BottomSheetFragment;
import com.example.taskmaneger.view.fragment.StateFragment;
import com.example.taskmaneger.view.fragment.TaskManagerFragment;
import com.example.taskmaneger.view.fragment.TimePickerFragment;

public class TaskManagerActivity extends SingleFragmentActivity
        implements StateFragment.StateFragmentCallback ,
        AddTaskFragment.AddTaskFragmentCallback {

    public static final String EXTRA_USER_ID =
            "com.example.taskmaneger.User Id";
    public static final String STATE_FRAGMENT_TAG = "State Fragment";
    private long mUserId;

    public static void start(Context context, long userId) {
        Intent starter = new Intent(context, TaskManagerActivity.class);
        starter.putExtra(EXTRA_USER_ID,userId);
        starter.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(starter);
    }

    @Override
    public Fragment getFragment() {
        Intent intent=getIntent();
        mUserId=intent.getLongExtra(EXTRA_USER_ID,0);
        return TaskManagerFragment.newInstance(mUserId);
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
    public void onMenuBtnSelectedListener(Fragment fragment,long taskId,String taskState) {
        BottomSheetFragment bottomSheetFragment=
                BottomSheetFragment.newInstance(taskId);

        Log.d(ProgramUtils.TAG,
                "TaskManagerActivity : " +
                        "create parent-child relationship between AddTaskFragment and TimePickerFragment");
        // create parent-child relationship between AddTaskFragment and TimePickerFragment
        bottomSheetFragment.setTargetFragment(fragment,
                StateFragment.
                        REQUEST_CODE_BUTTON_SHEET_FRAGMENT);

        bottomSheetFragment.show
                (getSupportFragmentManager(),
                        null);
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