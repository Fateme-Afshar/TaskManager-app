package com.example.taskmaneger.view.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskmaneger.R;
import com.example.taskmaneger.databinding.FragmentAddTaskBinding;
import com.example.taskmaneger.model.Task;
import com.example.taskmaneger.model.TaskState;
import com.example.taskmaneger.utils.DateUtils;
import com.example.taskmaneger.utils.PhotoUtils;
import com.example.taskmaneger.utils.ProgramUtils;
import com.example.taskmaneger.view.IOnClickListener;
import com.example.taskmaneger.viewModel.AddTaskViewModel;
import com.example.taskmaneger.viewModel.CommonPartAddAndUpdateTask;

import java.io.IOException;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddTaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddTaskFragment extends Fragment implements IOnClickListener {
    public static final String TAG_ADD_TASK_FRAGMENT="AddTaskFragment";
    public static final String ARG_USER_ID = "User Id";
    public static final String ARG_TASK_STATE = "Task State";

    private FragmentAddTaskBinding mBinding;

    private long mUserId;
    private TaskState mTaskState;

    public String[] PERMISSION={"android.permission.CAMERA"};
    public int REQUEST_PERMISSION_CAMERA =526;

    private AddTaskViewModel mViewModel;
    private AddTaskFragmentCallback mCallback;

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
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof AddTaskFragmentCallback)
            mCallback=(AddTaskFragmentCallback) context;
        else
            throw new ClassCastException
                    ("Must Implement AddTaskFragmentCallback interface");
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
        setupViewModel();
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
        mViewModel.setOnClickListener(this);

        mBinding.btnCamera.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (getActivity().checkSelfPermission(Manifest.permission.CAMERA)
                        ==PackageManager.PERMISSION_GRANTED){
                    mViewModel.onCameraClickListener();
                }else {
                    requestPermissions(new String[]{Manifest.permission.CAMERA},
                            REQUEST_PERMISSION_CAMERA);
                }
            }
        });
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
        }else if (requestCode==AddTaskViewModel.REQUEST_CODE_TIME_PICKER){
            Date userSelectedTime=mViewModel.getUserSelectedTime(data);
            mViewModel.setTaskTime(userSelectedTime);

            mBinding.btnTime.setText(DateUtils.getShortTimeFormat(userSelectedTime));
        }else if (requestCode==AddTaskViewModel.REQUEST_CODE_TAKE_PICTURE){
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults[0]==PackageManager.PERMISSION_DENIED)
            return;
        else
            mViewModel.onCameraClickListener();
    }

    @Override
    public void onButtonClickListener() {
        mCallback.onSaveBtnClickListener(mUserId,mTaskState.toString());
    }

    private void setupViewModel() {
        mViewModel.setTaskState(mTaskState);
        mViewModel.setUserId(mUserId);
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
                    AddTaskFragment.this.startActivityForResult(takePicture,
                            mViewModel.REQUEST_CODE_TAKE_PICTURE);
                }
            }

            @Override
            public void onTimePickerClickListener() {
                TimePickerFragment timePickerFragment=
                        TimePickerFragment.newInstance(mViewModel.getTask().getDate());

                // create parent-child relationship between AddTaskFragment and TimePickerFragment
                timePickerFragment.setTargetFragment(AddTaskFragment.this,
                        mViewModel.REQUEST_CODE_TIME_PICKER);

                timePickerFragment.show
                        (AddTaskFragment.this.getParentFragmentManager(),
                                AddTaskFragment.TAG_ADD_TASK_FRAGMENT);
            }

            @Override
            public void onDatePickerClickListener() {
                mViewModel.setTaskTime(new Date());
                DatePickerFragment datePickerFragment =
                        DatePickerFragment.newInstance(mViewModel.getTask().getDate());

                // create parent-child relationship between AddTaskFragment and DatePickerFragment
                datePickerFragment.setTargetFragment(AddTaskFragment.this,
                        mViewModel.REQUEST_CODE_DATE_PICKER);

                datePickerFragment.show
                        (AddTaskFragment.this.getParentFragmentManager(),
                                AddTaskFragment.TAG_ADD_TASK_FRAGMENT);
            }
        });
    }

    public interface AddTaskFragmentCallback{
        void onSaveBtnClickListener(long userId,String taskState);
    }
}