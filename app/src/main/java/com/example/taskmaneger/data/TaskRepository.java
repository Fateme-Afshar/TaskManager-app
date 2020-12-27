package com.example.taskmaneger.data;

import android.content.Context;

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
        TaskManagerDatabase dataBase= Room.
                databaseBuilder(mContext,TaskManagerDatabase.class,
                        TaskManagerSchema.NAME).
                allowMainThreadQueries().build();

        mDAO=dataBase.getTaskDao();
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


    public List<Task> getListWithUserId(long userId) {
        return mDAO.getListWithUserId(userId);
    }

    @Override
    public Task get(long taskId) {
        return mDAO.get(taskId);
    }

    @Override
    public void delete(Task element) {
        mDAO.delete(element);
    }

    @Override
    public void insert(Task element) {
        mDAO.insert(element);
    }

    @Override
    public void update(Task element) {
        mDAO.update(element);
    }

    public List<Task> getListWithState(String state,long userId){
        return mDAO.getListWithState(state,userId);
    }
}
