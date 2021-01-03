package com.example.taskmaneger.data;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.example.taskmaneger.data.room.TaskManagerDatabase;
import com.example.taskmaneger.data.room.TaskTableDAO;
import com.example.taskmaneger.model.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskRepository implements IRepository<Task>{
    private static TaskRepository sInstance;
    private Context mContext;
    private TaskTableDAO mDAO;

    private List<Task> mTaskList=new ArrayList<>();

    private TaskRepository(Context context) {
        mContext=context.getApplicationContext();
        mDAO=TaskManagerDatabase.getDatabase(mContext).getTaskDao();
    }

    public static TaskRepository getInstance(Context context) {
        if (sInstance==null)
            sInstance=new TaskRepository(context);
        return sInstance;
    }

    @Override
    public List<Task> getList() {
        return mDAO.getList();
    }


    public LiveData<List<Task>> getListWithUserId(long userId) {
        return mDAO.getListWithUserId(userId);
    }

    @Override
    public Task get(long taskId) {
        return mDAO.get(taskId);
    }

    @Override
    public void delete(Task element) {
        TaskManagerDatabase.databaseWriteExecutor.execute(()->mDAO.delete(element));
    }

    @Override
    public void insert(Task element) {
        TaskManagerDatabase.databaseWriteExecutor.execute(()->mDAO.insert(element));
    }

    @Override
    public void update(Task element) {
        TaskManagerDatabase.databaseWriteExecutor.execute(()->mDAO.update(element));
    }

    public LiveData<List<Task>> getListWithState(String state, long userId){
        return mDAO.getListWithState(state,userId);
    }
}
