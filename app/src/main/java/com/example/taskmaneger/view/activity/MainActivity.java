package com.example.taskmaneger.view.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.taskmaneger.R;
import com.example.taskmaneger.view.SingleFragmentActivity;
import com.example.taskmaneger.view.fragment.LoginFragment;
import com.example.taskmaneger.view.fragment.SignUpFragment;

public class MainActivity extends SingleFragmentActivity
        implements LoginFragment.LoginFragmentCallback,
        SignUpFragment.SignUpFragmentCallback {

    public static final String LOGIN_FRAGMENT_TAG = "Login Fragment";
    public static final String SIGN_UP_TAG = "Sign up";
    private static final int PERMISSIONS_REQUEST_CAMERA = 1;

    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        //starter.putExtra();
        context.startActivity(starter);
    }

    @Override
    public Fragment getFragment() {
        return LoginFragment.newInstance();
    }

    @Override
    public void onSignBtnClick() {
        getSupportFragmentManager().
                beginTransaction().
                add(SignUpFragment.newInstance(),
                        SIGN_UP_TAG);

        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.fragment_container,
                        SignUpFragment.newInstance()).
                addToBackStack(LOGIN_FRAGMENT_TAG).
                commit();
    }

    @Override
    public void startAdminActivity() {
        AdminActivity.start(this);
    }

    @Override
    public void startTaskManagerActivity(long userId) {
        TaskManagerActivity.start(this,userId);
    }

    @Override
    public void onSignUpClick() {

        getSupportFragmentManager().
                beginTransaction().
                add(LoginFragment.newInstance(),
                        LOGIN_FRAGMENT_TAG);

        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.fragment_container,
                        LoginFragment.newInstance(),
                        LOGIN_FRAGMENT_TAG).
                commit();
    }

}