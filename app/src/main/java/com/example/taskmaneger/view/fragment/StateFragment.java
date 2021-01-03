package com.example.taskmaneger.view.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskmaneger.R;
import com.example.taskmaneger.adapter.TasksAdapter;
import com.example.taskmaneger.data.UserWithTask;
import com.example.taskmaneger.databinding.FragmentStateBinding;
import com.example.taskmaneger.model.Task;
import com.example.taskmaneger.model.TaskState;
import com.example.taskmaneger.utils.ProgramUtils;
import com.example.taskmaneger.view.IOnClickListener;
import com.example.taskmaneger.viewModel.StateViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StateFragment extends Fragment implements IOnClickListener {
    public static final String ARG_USER_ID = "User Id";
    public static final String ARG_TASK_STATE = "Task State";
    private StateFragmentCallback mCallback;
    private long mUserId;
    private String mTaskState;

    private TasksAdapter mAdapter;

    private FragmentStateBinding mBinding;
    private StateViewModel mViewModel;

    public StateFragment() {
        // Required empty public constructor
    }

    public static StateFragment newInstance(String taskState,long userId) {
        StateFragment fragment = new StateFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_USER_ID,userId);
        args.putString(ARG_TASK_STATE,taskState);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof StateFragmentCallback)
            mCallback=(StateFragmentCallback) context;
        else
            throw new ClassCastException
                    ("At first must implement TaskManagerFragmentCallback interface");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments()!=null){
            mUserId=getArguments().getLong(ARG_USER_ID);
            mTaskState=getArguments().getString(ARG_TASK_STATE).toUpperCase();
        }

        mViewModel=new ViewModelProvider(this).get(StateViewModel.class);
        mViewModel.getTaskListWithState(mTaskState,mUserId).observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> taskList) {
                setupAdapter(taskList);
            }
        });
        mViewModel.setOnClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(ProgramUtils.TAG,"onCreateView");
      mBinding= DataBindingUtil.inflate
              (inflater,
                      R.layout.fragment_state,
                      container,
                      false);
        mBinding.setViewModel(mViewModel);
      return mBinding.getRoot();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(ProgramUtils.TAG,"onStop");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(ProgramUtils.TAG,"onPause");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(ProgramUtils.TAG,"onDestroy");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(ProgramUtils.TAG,"onResume");
    }

    private void setupAdapter(List<Task> taskList){
        mAdapter=new TasksAdapter(getActivity(),taskList);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinding.recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onButtonClickListener() {
        mCallback.onAddBtnClickListener(mUserId,mTaskState);
    }

    public interface StateFragmentCallback{
        void onAddBtnClickListener(long userId,String taskState);
    }
}