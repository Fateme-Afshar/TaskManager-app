package com.example.taskmaneger.data;

import androidx.lifecycle.LiveData;

import com.example.taskmaneger.data.room.TaskManagerDatabase;
import com.example.taskmaneger.data.room.TaskTableDAO;
import com.example.taskmaneger.model.Task;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class TaskRepository implements IRepository<Task>{
    private TaskTableDAO mDAO;

    private List<Task> mTaskList=new ArrayList<>();

    @Inject
    public TaskRepository() {
        mDAO=TaskManagerDatabase.getDatabase().getTaskDao();
    }

    @Override
    public LiveData<List<Task>> getList() {
        return mDAO.getList();
    }


    public LiveData<List<Task>> getListWithUserId(long userId) {
        return mDAO.getListWithUserId(userId);
    }

    @Override
    public LiveData<Task> get(long taskId) {
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

    public void deleteAll(long userId){
        TaskManagerDatabase.databaseWriteExecutor.execute(()->mDAO.deleteAll(userId));
    }

    public LiveData<List<Task>> getListWithState(String state, long userId){
        return mDAO.getListWithState(state,userId);
    }
}
