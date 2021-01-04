package com.example.taskmaneger.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.app.ShareCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskmaneger.R;
import com.example.taskmaneger.databinding.BottomSheetFragmentBinding;
import com.example.taskmaneger.utils.ProgramUtils;
import com.example.taskmaneger.view.IOnClickListener;
import com.example.taskmaneger.viewModel.BottomSheetViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BottomSheetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BottomSheetFragment extends BottomSheetDialogFragment implements IOnClickListener{
    public static final String ARG_TASK_ID = "Task Id";

    private BottomSheetViewModel mViewModel;

    private BottomSheetFragmentBinding mBinding;

    public BottomSheetFragment() {
        // Required empty public constructor
    }

    public static BottomSheetFragment newInstance(long taskId) {
        BottomSheetFragment fragment = new BottomSheetFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_TASK_ID,taskId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel=new ViewModelProvider(this).get(BottomSheetViewModel.class);
        if (getArguments() != null) {
            long taskId=getArguments().getLong(ARG_TASK_ID);
            mViewModel.setLifecycleOwner(this);
            mViewModel.setTaskId(taskId);
            mViewModel.setOnClickListener(this);
            mViewModel.setCallback(new BottomSheetViewModel.BottomSheetFragmentViewModelCallback() {
                @Override
                public void onShareTask(String taskInfo) {
                    Intent intent= ShareCompat.IntentBuilder
                            .from(getActivity()).
                                    setChooserTitle("Choose one of App for share task information : ").
                                    setSubject("Sharing Task Information").
                                    setText(taskInfo).
                                    setType("text/plain").
                                    getIntent();

                    if (intent.resolveActivity(getActivity().getPackageManager())!=null)
                        startActivity(intent);
                }
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding= DataBindingUtil.inflate
                (inflater,
                        R.layout.bottom_sheet_fragment,
                        container,
                        false);
        mBinding.setViewModel(mViewModel);
        return mBinding.getRoot();
    }

    @Override
    public void onButtonClickListener() {
        Fragment targetFragment=this.getTargetFragment();
        Intent data=new Intent();
        Log.d(ProgramUtils.TAG,"BottomSheetFragment : Sending result for parent");
        targetFragment.onActivityResult(this.getTargetRequestCode(), Activity.RESULT_OK,data);
        dismiss();
    }
}