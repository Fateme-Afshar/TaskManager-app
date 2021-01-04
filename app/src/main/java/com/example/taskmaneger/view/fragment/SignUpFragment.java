package com.example.taskmaneger.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.taskmaneger.R;
import com.example.taskmaneger.databinding.FragmentSignupBinding;
import com.example.taskmaneger.view.IOnClickListener;
import com.example.taskmaneger.viewModel.SignUpViewModel;

public class SignUpFragment extends Fragment implements IOnClickListener {
    private FragmentSignupBinding mBinding;
    private SignUpViewModel mViewModel;

    private SignUpFragmentCallback mCallback;

    public SignUpFragment() {
        // Required empty public constructor
    }

    public static SignUpFragment newInstance() {
        SignUpFragment fragment = new SignUpFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof SignUpFragmentCallback)
            mCallback=(SignUpFragmentCallback) context;
        else
            throw new ClassCastException
                    ("At first must implement SignUpFragmentCallback interface");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel=new ViewModelProvider(getActivity()).get(SignUpViewModel.class);
        mViewModel.setLifecycleOwner(this);
        mViewModel.setOnSignBtnClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding= DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_signup,
                container,
                false);

        mBinding.setViewModel(mViewModel);
        return mBinding.getRoot();
    }

    @Override
    public void onButtonClickListener() {
        mCallback.onSignUpClick();
    }

    public interface SignUpFragmentCallback{
        void onSignUpClick();
    }
}