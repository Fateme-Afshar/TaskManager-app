package com.example.taskmaneger.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.taskmaneger.R;
import com.example.taskmaneger.data.UserRepository;
import com.example.taskmaneger.model.User;
import com.example.taskmaneger.utils.ProgramUtils;
import com.example.taskmaneger.utils.ViewUtils;
import com.example.taskmaneger.view.IOnClickListener;

public class SignUpViewModel extends AndroidViewModel {
    private UserRepository mRepository = UserRepository.getInstance(getApplication());
    private User mUser = new User();
    private IOnClickListener mOnSignBtnClickListener;

    public SignUpViewModel(@NonNull Application application) {
        super(application);
    }

    public void setOnSignBtnClickListener(IOnClickListener onSignBtnClickListener) {
        mOnSignBtnClickListener = onSignBtnClickListener;
    }

    public void onTextChangedUsername(CharSequence username, int i, int j, int k) {
        mUser.setUsername(username.toString());
    }

    public void onTextChangedPassword(CharSequence password, int i, int j, int k) {
            mUser.setPassword(password.toString());
    }

    public void onTextChangedAdminCode(CharSequence adminCode, int i, int j, int k) {
        mUser.setAdmin(checkAdmin(adminCode.toString()));
    }

    public void onSignClickListener() {
        if (!mUser.getUsername().equals("") && !mUser.getPassword().equals("")) {
            if (!checkUserExist(mUser.getUsername())) {
                if (checkPassword(mUser.getPassword())) {
                    Log.d(ProgramUtils.TAG, "New User insert in database");
                    mRepository.insert(mUser);

                    try {
                            mOnSignBtnClickListener.onButtonClickListener();
                    }catch (NullPointerException e){
                        Log.e(ProgramUtils.TAG," SignUpViewModel : "+e.toString());
                    }

                    if (mUser.isAdmin()) {
                        Log.d(ProgramUtils.TAG, "User is Admin");
                        //TODO: check user is admin. if user is admin start Admin activity else start TaskManager activity.
                    }
                }
            }
        } else
            ViewUtils.returnToast(getApplication(), "Username or password cann't be null");
    }

    public boolean checkPassword(String text) {
        if (text.length() >= 8)
            return true;
        else {
            ViewUtils.returnToast(getApplication(), "Password length must be more than 8");
            return false;
        }
    }

    public boolean checkUserExist(String username) {

        if (!mRepository.checkExistUser(username))
            return false;
        else {
            ViewUtils.returnToast(getApplication(), R.string.user_exist);
            return true;
        }
    }

    public boolean checkAdmin(String adminCode) {
        return adminCode.equals("@utab");
    }
}
