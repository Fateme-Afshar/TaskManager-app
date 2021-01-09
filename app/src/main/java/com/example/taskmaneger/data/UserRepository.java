package com.example.taskmaneger.data;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.example.taskmaneger.data.room.TaskManagerDatabase;
import com.example.taskmaneger.data.room.UserTableDAO;
import com.example.taskmaneger.model.User;

import java.util.List;

public class UserRepository implements IRepository<User> {
    private static UserRepository sInstance;
    private Context mContext;

    private UserTableDAO mDAO;

    private UserRepository(Context context) {
        mContext = context.getApplicationContext();
        TaskManagerDatabase database =
                TaskManagerDatabase.getDatabase(context);

        mDAO = database.getUserDao();
    }

    public static UserRepository getInstance(Context context) {
        if (sInstance == null)
            sInstance = new UserRepository(context);
        return sInstance;
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
