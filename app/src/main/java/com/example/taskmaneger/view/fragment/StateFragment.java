package com.example.taskmaneger.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.taskmaneger.R;
import com.example.taskmaneger.adapter.TasksAdapter;
import com.example.taskmaneger.databinding.FragmentStateBinding;
import com.example.taskmaneger.model.Task;
import com.example.taskmaneger.view.IOnClickListener;
import com.example.taskmaneger.viewModel.StateViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StateFragment extends Fragment implements IOnClickListener {
    public static final int REQUEST_CODE_BUTTON_SHEET_FRAGMENT =1;
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode!= Activity.RESULT_OK && data==null)
            return;
        if (requestCode==REQUEST_CODE_BUTTON_SHEET_FRAGMENT){
            mBinding.notifyChange();
        }
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
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate
                (inflater,
                        R.layout.fragment_state,
                        container,
                        false);
        mBinding.setViewModel(mViewModel);
        return mBinding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);

        MenuItem searchItem = menu.findItem(R.id.menu_search);

        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (mAdapter != null)
                    mAdapter.getFilter().filter(newText);
                return false;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_logout:
               mCallback.onLogoutMenuItemClickListener();
                return true;
            case R.id.menu_delete:
                mViewModel.deleteAllTask(mUserId);
                return true;
            default:
                return false;
        }
    }

    private void setupAdapter(List<Task> taskList) {
        mAdapter = new TasksAdapter(getActivity(), taskList);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinding.recyclerView.setAdapter(mAdapter);

        mAdapter.setCallback(new TasksAdapter.TasksAdapterCallback() {
            @Override
            public void onMenuBtnSelectedListener(long taskId) {
                mCallback.onMenuBtnSelectedListener(StateFragment.this, taskId, mTaskState);
            }
        });

        if (taskList.size()==0)
            mBinding.imvEmpty.setVisibility(View.VISIBLE);
        else
            mBinding.imvEmpty.setVisibility(View.GONE);
    }

    @Override
    public void onButtonClickListener() {
        mCallback.onAddBtnClickListener(mUserId,mTaskState);
    }

    public interface StateFragmentCallback{
        void onAddBtnClickListener(long userId,String taskState);
        void onMenuBtnSelectedListener(Fragment fragment,long taskId,String taskState);
        void onLogoutMenuItemClickListener();
    }
}