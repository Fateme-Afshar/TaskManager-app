package com.example.taskmaneger.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskmaneger.R;
import com.example.taskmaneger.databinding.FragmentAddTaskBinding;
import com.example.taskmaneger.model.TaskState;
import com.example.taskmaneger.utils.DateUtils;
import com.example.taskmaneger.viewModel.AddTaskViewModel;

import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddTaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddTaskFragment extends Fragment {
    public static final String TAG_ADD_TASK_FRAGMENT="AddTaskFragment";
    public static final String ARG_USER_ID = "User Id";
    public static final String ARG_TASK_STATE = "Task State";

    private FragmentAddTaskBinding mBinding;

    private long mUserId;
    private TaskState mTaskState;

    private AddTaskViewModel mViewModel;

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

        mViewModel=new ViewModelProvider(this).get(AddTaskViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding= DataBindingUtil.inflate
                (inflater,
                        R.layout.fragment_add_task,
                        container,
                        false);
        mBinding.setFragment(this);
        mBinding.setViewModel(mViewModel);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode!= Activity.RESULT_OK && data==null)
            return;
        if (requestCode== AddTaskViewModel.REQUEST_CODE_DATE_PICKER){
            Date userSelectedDate= mViewModel.getUserSelectedDate(data);
            mViewModel.setTaskDate(userSelectedDate);

            mBinding.btnDate.setText(DateUtils.getShortDateFormat(userSelectedDate));
        }
    }
}