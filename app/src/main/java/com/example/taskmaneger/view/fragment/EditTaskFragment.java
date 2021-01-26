package com.example.taskmaneger.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.taskmaneger.R;
import com.example.taskmaneger.databinding.FragmentEditTaskBinding;
import com.example.taskmaneger.model.Task;
import com.example.taskmaneger.utils.DateUtils;
import com.example.taskmaneger.utils.PhotoUtils;
import com.example.taskmaneger.utils.ProgramUtils;
import com.example.taskmaneger.view.IOnClickListener;
import com.example.taskmaneger.viewModel.AddTaskViewModel;
import com.example.taskmaneger.viewModel.CommonPartAddAndUpdateTask;
import com.example.taskmaneger.viewModel.EditTaskViewModel;

import java.io.IOException;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditTaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditTaskFragment extends Fragment implements IOnClickListener {
    public static final String ARG_TASK = "Task Id";
    private EditTaskViewModel mViewModel;
    private FragmentEditTaskBinding mBinding;

    private EditTaskFragmentCallback mCallback;

    public EditTaskFragment() {
        // Required empty public constructor
    }

    public static EditTaskFragment newInstance(Task task) {
        EditTaskFragment fragment = new EditTaskFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_TASK, task);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof EditTaskFragmentCallback)
            mCallback=(EditTaskFragmentCallback) context;
        else
            throw new ClassCastException
                    ("Must Implement EditTaskFragmentCallback interface");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(EditTaskViewModel.class);
        setupViewModel();

        if (getArguments() != null) {
            mViewModel.setTask((Task) getArguments().getSerializable(ARG_TASK));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_edit_task,
                container,
                false);
        mBinding.setFragment(this);
        mBinding.setViewModel(mViewModel);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != Activity.RESULT_OK && data == null)
            return;
        if (requestCode == AddTaskViewModel.REQUEST_CODE_DATE_PICKER) {
            Date userSelectedDate = mViewModel.getUserSelectedDate(data);
            mViewModel.setTaskDate(userSelectedDate);

            mBinding.btnDate.setText(DateUtils.getShortDateFormat(userSelectedDate));
        } else if (requestCode == AddTaskViewModel.REQUEST_CODE_TIME_PICKER) {
            Date userSelectedTime = mViewModel.getUserSelectedTime(data);
            mViewModel.setTaskTime(userSelectedTime);

            mBinding.btnTime.setText(DateUtils.getShortTimeFormat(userSelectedTime));
        } else if (requestCode == AddTaskViewModel.REQUEST_CODE_TAKE_PICTURE) {
            Uri photoUri = mViewModel.getUriPhoto();

            getActivity().revokeUriPermission(photoUri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            updatePhoto();
        }
    }

    public void updatePhoto() {
        if (mViewModel.getPhotoFile() == null || !mViewModel.getPhotoFile().exists())
            return;

        Bitmap image = PhotoUtils.getScalePhoto(
                mViewModel.getPhotoFile().getAbsolutePath(),
                mBinding.imvTaskImg.getHeight(),
                mBinding.imvTaskImg.getWidth());

        mBinding.imvTaskImg.setImageBitmap(image);
    }

    @Override
    public void onButtonClickListener() {
        mCallback.onEditBtnClickListener(
                mViewModel.getTask().getUserId(),
                mViewModel.getTask().getTaskState().toString());
    }

    private void setupViewModel() {
        mViewModel.setOnClickListener(this);

        mViewModel.setCallback(new CommonPartAddAndUpdateTask.CommonPartCallback() {
            @Override
            public void onCameraClickListener() {
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePicture.resolveActivity(getActivity().getPackageManager()) != null) {
                    mViewModel.setPhotoFile(null);
                    try {
                        mViewModel.setPhotoFile(mViewModel.createImageFile());
                    } catch (IOException e) {
                        Log.e(ProgramUtils.TAG,
                                "Taking photo part: Error occurred while creating the File : " + e.getMessage());
                    }
                }
                if (mViewModel.getPhotoFile() != null) {
                    Uri photoUri = FileProvider.getUriForFile(getActivity(),
                            mViewModel.AUTHORITY, mViewModel.getPhotoFile());
                    takePicture.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                    EditTaskFragment.this.startActivityForResult(takePicture,
                            mViewModel.REQUEST_CODE_TAKE_PICTURE);
                }
            }

            @Override
            public void onTimePickerClickListener() {
                TimePickerFragment timePickerFragment=
                        TimePickerFragment.newInstance(mViewModel.getTask().getDate());

                // create parent-child relationship between AddTaskFragment and TimePickerFragment
                timePickerFragment.setTargetFragment(EditTaskFragment.this,
                        mViewModel.REQUEST_CODE_TIME_PICKER);

                timePickerFragment.show
                        (EditTaskFragment.this.getParentFragmentManager(),
                                AddTaskFragment.TAG_ADD_TASK_FRAGMENT);
            }

            @Override
            public void onDatePickerClickListener() {
                DatePickerFragment datePickerFragment =
                        DatePickerFragment.newInstance(mViewModel.getTask().getDate());

                // create parent-child relationship between AddTaskFragment and DatePickerFragment
                datePickerFragment.setTargetFragment(EditTaskFragment.this,
                        mViewModel.REQUEST_CODE_DATE_PICKER);

                datePickerFragment.show
                        (EditTaskFragment.this.getParentFragmentManager(),
                                AddTaskFragment.TAG_ADD_TASK_FRAGMENT);
            }
        });
    }

    public interface EditTaskFragmentCallback{
        void onEditBtnClickListener(long userId,String taskState);
    }
}