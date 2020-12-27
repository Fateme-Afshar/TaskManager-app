package com.example.taskmaneger.view.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskmaneger.R;
import com.example.taskmaneger.model.TaskState;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddTaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddTaskFragment extends Fragment {
    public static final String ARG_USER_ID = "User Id";
    public static final String ARG_TASK_STATE = "Task State";
    private long mUserId;
    private TaskState mTaskState;

    public AddTaskFragment() {
        // Required empty public constructor
    }

    public static AddTaskFragment newInstance(long userId,String taskState) {
        AddTaskFragment fragment = new AddTaskFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_USER_ID,userId);
        args.putString(ARG_TASK_STATE,taskState);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String taskState="TODO";
        if (getArguments()!=null){
            mUserId=getArguments().getLong(ARG_USER_ID);
            taskState=getArguments().getString(ARG_TASK_STATE).toUpperCase();
        }
        mTaskState=TaskState.valueOf(taskState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_task, container, false);
    }
}