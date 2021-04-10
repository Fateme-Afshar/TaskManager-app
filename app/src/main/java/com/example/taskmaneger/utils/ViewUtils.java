package com.example.taskmaneger.utils;

import android.widget.Toast;

import com.example.taskmaneger.TaskManagerApplication;

public class ViewUtils {

        public static void returnToast(String msg){
            Toast.makeText(TaskManagerApplication.getApplicationGraph().getContext(),msg,Toast.LENGTH_LONG).show();
        }
    public static void returnToast(int msg){
        Toast.makeText(TaskManagerApplication.getApplicationGraph().getContext(),msg,Toast.LENGTH_LONG).show();
    }
}
