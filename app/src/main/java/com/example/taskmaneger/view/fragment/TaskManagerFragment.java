package com.example.taskmaneger.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.taskmaneger.R;
import com.example.taskmaneger.adapter.TaskManagerAdapter;
import com.example.taskmaneger.databinding.FragmentTaskManagerBinding;
import com.example.taskmaneger.model.TaskState;
import com.example.taskmaneger.viewModel.TaskManagerViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskManagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskManagerFragment extends Fragment {
    public static final String ARGS_USER_ID = "User Id";
    private long mUserId;
    private FragmentTaskManagerBinding mBinding;

    private TaskManagerViewModel mViewModel;

    private TaskManagerAdapter mTaskAdapter;

    private String[] mState = {TaskState.TODO.toString(),
            TaskState.DOING.toString(), TaskState.DONE.toString()};

    public TaskManagerFragment() {
        // Required empty public constructor
    }

    public static TaskManagerFragment newInstance(long userId) {
        TaskManagerFragment fragment = new TaskManagerFragment();
        Bundle args = new Bundle();
        args.putLong(ARGS_USER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this).
                get(TaskManagerViewModel.class);

        if (getArguments() != null) {
            mUserId=getArguments().getLong(ARGS_USER_ID);
            mViewModel.setUserId(mUserId);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate
                (inflater,
                        R.layout.fragment_task_manager,
                        container,
                        false);
        setupAdapter();
        return mBinding.getRoot();
    }

    private void setupAdapter() {
        mTaskAdapter = new TaskManagerAdapter(getActivity(), mUserId);
        mBinding.viewPager2.setAdapter(mTaskAdapter);
        new TabLayoutMediator(mBinding.tabLayout, mBinding.viewPager2,
                (tab, position) -> tab.setText(mState[position])
        ).attach();
    }
}