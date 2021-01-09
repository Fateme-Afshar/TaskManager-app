package com.example.taskmaneger.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmaneger.R;
import com.example.taskmaneger.databinding.ItemViewBinding;
import com.example.taskmaneger.model.Task;
import com.example.taskmaneger.viewModel.StateViewModel;

import java.util.ArrayList;
import java.util.List;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskHolder> implements Filterable {
    private Context mContext;
    private List<Task> mTaskList;
    private List<Task> mSearchTask;

    private TasksAdapterCallback mCallback;

    public TasksAdapter(Context context, List<Task> taskList) {
        mContext = context.getApplicationContext();
        mTaskList=taskList;
        mSearchTask=new ArrayList<>(taskList);
    }

    public void setCallback(TasksAdapterCallback callback) {
        mCallback = callback;
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

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    private Filter mFilter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Task> filterTaskList=new ArrayList<>();
            if (constraint==null || constraint.length()==0){
                filterTaskList.addAll(mSearchTask);
            }else {
                String filterPattern=constraint.toString().toLowerCase().trim();

                for (Task task : mSearchTask) {
                    if (task.getTitle().toLowerCase().
                            contains(filterPattern) ||
                            task.getTitle().toLowerCase().
                                    contains(filterPattern)){
                        filterTaskList.add(task);
                    }
                }
            }

            FilterResults results=new FilterResults();
            results.values=filterTaskList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mTaskList.clear();
            mTaskList.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };

    class TaskHolder extends RecyclerView.ViewHolder {
        private ItemViewBinding mBinding;
        public TaskHolder(@NonNull ItemViewBinding itemViewBinding) {
            super(itemViewBinding.getRoot());
            mBinding=itemViewBinding;
        }

        public void binding(Task task){
                mBinding.setTask(task);

                mBinding.btnMenu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCallback.onMenuBtnSelectedListener(task.getId());
                    }
                });
        }
    }

    public interface TasksAdapterCallback{
        void onMenuBtnSelectedListener(long taskId);
    }
}
