package com.example.taskmaneger.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskmaneger.R;
import com.example.taskmaneger.model.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskManagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskManagerFragment extends Fragment {
    public static final String ARGS_USER_ID = "User Id";
    private User mUser;

    public TaskManagerFragment() {
        // Required empty public constructor
    }
    public static TaskManagerFragment newInstance(long userId) {
        TaskManagerFragment fragment = new TaskManagerFragment();
        Bundle args = new Bundle();
        args.putLong(ARGS_USER_ID,userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_manager, container, false);
    }
}