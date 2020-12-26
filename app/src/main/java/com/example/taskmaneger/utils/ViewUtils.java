package com.example.taskmaneger.utils;

import android.content.Context;
import android.widget.Toast;

import com.example.taskmaneger.data.UserRepository;
import com.example.taskmaneger.model.User;

public class ViewUtils {
        public static void returnToast(Context context,String msg){
            Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
        }
    public static void returnToast(Context context,int msg){
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
    }
}
