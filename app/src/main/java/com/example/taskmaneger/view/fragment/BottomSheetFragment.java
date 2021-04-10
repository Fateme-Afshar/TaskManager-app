package com.example.taskmaneger.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ShareCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskmaneger.R;
import com.example.taskmaneger.TaskManagerApplication;
import com.example.taskmaneger.databinding.BottomSheetFragmentBinding;
import com.example.taskmaneger.model.Task;
import com.example.taskmaneger.utils.ProgramUtils;
import com.example.taskmaneger.view.IOnClickListener;
import com.example.taskmaneger.viewModel.BottomSheetViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BottomSheetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BottomSheetFragment extends BottomSheetDialogFragment implements IOnClickListener{
    public static final String ARG_TASK_ID = "Task Id";
    @Inject
    public BottomSheetViewModel mViewModel;

    private BottomSheetFragmentBinding mBinding;

    private BottomSheetFragmentCallback mCallback;

    private long mTaskId;

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
    public void onAttach(@NonNull Context context) {
        TaskManagerApplication.getApplicationGraph().inject(this);
        super.onAttach(context);

        if (context instanceof BottomSheetFragmentCallback)
            mCallback=(BottomSheetFragmentCallback) context;
        else
            throw new ClassCastException
                    ("Must Implement BottomSheetFragmentCallback interface");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTaskId=getArguments().getLong(ARG_TASK_ID);
            mViewModel.setLifecycleOwner(this);
            mViewModel.setTaskId(mTaskId);
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

/*         I know this is anti pattern in MVVM architecture ,
          but unfortunately i haven't any idea for manage this.*/
        mBinding.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onEditClickListener(mViewModel.getTask());
                dismiss();
            }
        });
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

    public interface BottomSheetFragmentCallback{
        void onEditClickListener(Task task);
    }
}