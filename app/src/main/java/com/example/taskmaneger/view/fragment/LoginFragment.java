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
import com.example.taskmaneger.databinding.FragmentLoginBinding;
import com.example.taskmaneger.view.IOnClickListener;
import com.example.taskmaneger.viewModel.LoginViewModel;

public class LoginFragment extends Fragment
        implements IOnClickListener{
    private FragmentLoginBinding mBinding;
    private LoginViewModel mViewModel;

    private LoginFragmentCallback mCallback;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof LoginFragmentCallback)
            mCallback=(LoginFragmentCallback) context;
        else
            throw new ClassCastException
                ("Must Implement HandleOnClickListener interface");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel=new ViewModelProvider(getActivity()).get(LoginViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding= DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_login,
                container,
                false);
        mBinding.setFragment(this);
        mBinding.setViewModel(mViewModel);
        return mBinding.getRoot();
    }

    public LoginFragmentCallback getCallback() {
        return mCallback;
    }

    @Override
    public void onSignClickListener() {
        mCallback.onSignBtnClick();
    }

    public   interface LoginFragmentCallback{
        void onSignBtnClick();
    }
}