package com.example.taskmaneger.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmaneger.R;
import com.example.taskmaneger.databinding.ItemViewBinding;
import com.example.taskmaneger.model.Task;
import com.example.taskmaneger.viewModel.StateViewModel;

import java.util.List;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskHolder> {
    private Context mContext;
    private List<Task> mTaskList;

    public TasksAdapter(Context context, List<Task> taskList) {
        mContext = context.getApplicationContext();
        mTaskList=taskList;
    }

    @NonNull
    @Override
    public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemViewBinding itemViewBinding= DataBindingUtil.inflate(
                LayoutInflater.from(mContext),
                R.layout.item_view,parent,
                false);

        return new TaskHolder(itemViewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskHolder holder, int position) {
            holder.binding(mTaskList.get(position));
    }

    @Override
    public int getItemCount() {
        return mTaskList.size();
    }

    static class TaskHolder extends RecyclerView.ViewHolder {
        private ItemViewBinding mBinding;
        public TaskHolder(@NonNull ItemViewBinding itemViewBinding) {
            super(itemViewBinding.getRoot());
            mBinding=itemViewBinding;
        }

        public void binding(Task task){
                mBinding.setTask(task);
        }
    }
}
