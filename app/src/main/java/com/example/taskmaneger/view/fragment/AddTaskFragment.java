package com.example.taskmaneger.view.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.taskmaneger.R;
import com.example.taskmaneger.databinding.FragmentAddTaskBinding;
import com.example.taskmaneger.model.TaskState;
import com.example.taskmaneger.utils.DateUtils;
import com.example.taskmaneger.utils.PhotoUtils;
import com.example.taskmaneger.view.IOnClickListener;
import com.example.taskmaneger.view.activity.MainActivity;
import com.example.taskmaneger.viewModel.AddTaskViewModel;

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
    public int REQUEST_PERMISSION_EXTERNAL=526;

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
                    ("Must Implement HandleOnClickListener interface");
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
        mViewModel.setTaskState(mTaskState);
        mViewModel.setUserId(mUserId);
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (arePermissionAgree()) {

                ActivityCompat
                        .requestPermissions(
                                getActivity(),
                               PERMISSION,
                                requestCode);

            }else {
                Toast.makeText(getActivity(),"Sorry cann't use this feature",Toast.LENGTH_LONG).show();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean arePermissionAgree() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED)
            return true;
        return false;
    }

    @Override
    public void onButtonClickListener() {
        mCallback.onSaveBtnClickListener(mUserId,mTaskState.toString());
    }

    public interface AddTaskFragmentCallback{
        void onSaveBtnClickListener(long userId,String taskState);
    }
}