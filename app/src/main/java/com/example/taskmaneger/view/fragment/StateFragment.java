package com.example.taskmaneger.view.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskmaneger.R;
import com.example.taskmaneger.databinding.FragmentStateBinding;
import com.example.taskmaneger.model.TaskState;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StateFragment extends Fragment {
    public static final String ARG_USER_ID = "User Id";
    public static final String ARG_TASK_STATE = "Task State";
    private StateFragmentCallback mCallback;
    private long mUserId;
    private TaskState mTaskState;

    private FragmentStateBinding mBinding;

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
            mTaskState=TaskState.valueOf
                    (getArguments().getString(ARG_TASK_STATE).toUpperCase());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      mBinding= DataBindingUtil.inflate
              (inflater,
                      R.layout.fragment_state,
                      container,
                      false);

      mBinding.btnAddTask.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
                mCallback.onAddBtnClickListener(mUserId,mTaskState.toString());
          }
      });

      return mBinding.getRoot();
    }

    public interface StateFragmentCallback{
        void onAddBtnClickListener(long userId,String taskState);
    }
}