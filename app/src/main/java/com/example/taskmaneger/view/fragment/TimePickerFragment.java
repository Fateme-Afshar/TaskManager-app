package com.example.taskmaneger.view.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.taskmaneger.R;
import com.example.taskmaneger.TaskManagerApplication;
import com.example.taskmaneger.databinding.FragmentTimePickerBinding;
import com.example.taskmaneger.viewModel.TimePickerViewModel;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TimePickerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimePickerFragment extends DialogFragment {
    public static final String ARG_CURRENT_TIME = "Current Time";

    private FragmentTimePickerBinding mBinding;
    @Inject
    public TimePickerViewModel mViewModel;

    private Date mCurrentDate;

    public TimePickerFragment() {
        // Required empty public constructor
    }

    public static TimePickerFragment newInstance(Date currentTime) {
        TimePickerFragment fragment = new TimePickerFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CURRENT_TIME, currentTime);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        TaskManagerApplication.getApplicationGraph().inject(this);
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            mCurrentDate = (Date) getArguments().getSerializable(ARG_CURRENT_TIME);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        mBinding = DataBindingUtil.inflate
                (inflater,
                        R.layout.fragment_time_picker,
                        null,
                        false);

        return new AlertDialog.Builder(getContext()).
                setTitle(R.string.time_picker_title).
                setIcon(R.mipmap.ic_launcher).
                setView(mBinding.getRoot()).
                setPositiveButton(android.R.string.ok, (dialogInterface, i) -> {
                    Date timePicked = getUserSelectedTime();
                    mViewModel.setResult(this, timePicked);
                }).
                setNegativeButton(android.R.string.cancel, null).
                create();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private Date getUserSelectedTime() {
        Calendar calendar = Calendar.getInstance();
        int hour = mBinding.timePicker.getHour();
        calendar.set(Calendar.HOUR, hour);
        int minute = mBinding.timePicker.getMinute();
        calendar.set(Calendar.MINUTE, minute);

        return calendar.getTime();
    }
}