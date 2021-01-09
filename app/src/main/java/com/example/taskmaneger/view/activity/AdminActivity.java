package com.example.taskmaneger.view.activity;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.example.taskmaneger.view.SingleFragmentActivity;
import com.example.taskmaneger.view.fragment.AdminFragment;

public class AdminActivity extends SingleFragmentActivity
        implements AdminFragment.AdminFragmentCallback {

    public static void start(Context context) {
        Intent starter = new Intent(context, AdminActivity.class);
        context.startActivity(starter);
    }

    @Override
    public Fragment getFragment() {
        return AdminFragment.newInstance();
    }

    @Override
    public void onItemUserSelected(long userId) {
        TaskManagerActivity.start(this,userId);
    }

    @Override
    public void onLogoutMenuItemClickListener() {
        finish();
    }
}