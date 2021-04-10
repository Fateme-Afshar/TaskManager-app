package com.example.taskmaneger.viewModel;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.taskmaneger.R;
import com.example.taskmaneger.TaskManagerApplication;
import com.example.taskmaneger.data.TaskRepository;
import com.example.taskmaneger.model.Task;
import com.example.taskmaneger.view.IOnClickListener;

import java.text.DateFormat;

import javax.inject.Inject;

public class BottomSheetViewModel extends ViewModel {
    private TaskRepository mRepository;
    private long mTaskId;
    private Task mTask;

    private LifecycleOwner mLifecycleOwner;
    private IOnClickListener mOnClickListener;

    private BottomSheetFragmentViewModelCallback mCallback;

    @Inject
    public BottomSheetViewModel(TaskRepository taskRepository) {
        mRepository=taskRepository;
    }

    public Task getTask() {
        return mTask;
    }

    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        mLifecycleOwner = lifecycleOwner;
    }

    public void setTaskId(long taskId) {
        mTaskId = taskId;
        mRepository.get(mTaskId).observe(mLifecycleOwner, new Observer<Task>() {
            @Override
            public void onChanged(Task task) {
                mTask=task;
            }
        });
    }

    public void setOnClickListener(IOnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public void onDeleteBtnClickListener(){
        mRepository.delete(mTask);
        mOnClickListener.onButtonClickListener();
    }

    public void onShareBtnClickListener(){
        mCallback.onShareTask(getInfoTask());
        mOnClickListener.onButtonClickListener();
    }

    public interface BottomSheetFragmentViewModelCallback{
        void onShareTask(String taskInfo);
    }

    public void setCallback(BottomSheetFragmentViewModelCallback callback) {
        mCallback = callback;
    }

    private String getInfoTask(){
        return TaskManagerApplication.getApplicationGraph().getContext().getString(R.string.all_task_info,
                mTask.getTitle(),
                mTask.getDescription(),
                mTask.getTaskState().toString(),
                DateFormat.getDateInstance(DateFormat.SHORT).
                        format(mTask.getDate()),
                DateFormat.getTimeInstance(DateFormat.SHORT).
                        format(mTask.getTime()));
    }
}
