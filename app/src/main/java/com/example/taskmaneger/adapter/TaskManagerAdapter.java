package com.example.taskmaneger.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.taskmaneger.model.TaskState;
import com.example.taskmaneger.view.fragment.StateFragment;

public class TaskManagerAdapter extends FragmentStateAdapter {
    public static final int ITEM_COUNT = 3;
    private final long mUserId;

    public TaskManagerAdapter(@NonNull FragmentActivity fragmentActivity, long userId) {
        super(fragmentActivity);
        mUserId=userId;
    }

    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return StateFragment.newInstance(TaskState.TODO.toString(),mUserId);
            case 1:
               return StateFragment.newInstance(TaskState.DOING.toString(),mUserId);
            case 2:
                return StateFragment.newInstance(TaskState.DONE.toString(),mUserId);
            default:
               return null;
        }
    }

    @Override
    public int getItemCount() {
        return ITEM_COUNT;
    }
}
