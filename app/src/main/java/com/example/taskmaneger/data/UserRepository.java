package com.example.taskmaneger.data;

import androidx.lifecycle.LiveData;

import com.example.taskmaneger.data.room.TaskManagerDatabase;
import com.example.taskmaneger.data.room.UserTableDAO;
import com.example.taskmaneger.model.User;

import java.util.List;

import javax.inject.Inject;

public class UserRepository implements IRepository<User> {
    private UserTableDAO mDAO;

    @Inject
    public UserRepository() {
        TaskManagerDatabase database =
                TaskManagerDatabase.getDatabase();

        mDAO = database.getUserDao();
    }

    @Override
    public LiveData<User> get(long id) {
        return mDAO.get(id);
    }

    public LiveData<User> get(String username) {
        return mDAO.get(username);
    }

    @Override
    public LiveData<List<User>> getList() {
        return mDAO.getList();
    }

    @Override
    public void insert(User user) {
        TaskManagerDatabase.databaseWriteExecutor.execute(()->mDAO.insert(user));
    }

    @Override
    public void delete(User user) {
        TaskManagerDatabase.databaseWriteExecutor.execute(()->mDAO.delete(user));
    }

    public void deleteAll() {
        TaskManagerDatabase.databaseWriteExecutor.execute(()->mDAO.deleteAll());
    }

    @Override
    public void update(User user) {
        TaskManagerDatabase.databaseWriteExecutor.execute(()->mDAO.update(user));
    }

    public LiveData<List<UserWithTask>> getUserWithTask(){
            return mDAO.getUserWithTask();
    }
}
