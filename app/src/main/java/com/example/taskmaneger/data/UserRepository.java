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
        TaskManagerDatabase database = Room.databaseBuilder
                (mContext,
                        TaskManagerDatabase.class,
                        TaskManagerSchema.NAME).
                allowMainThreadQueries().
                build();

        mDAO = database.getUserDao();
    }

    public static UserRepository getInstance(Context context) {
        if (sInstance == null)
            sInstance = new UserRepository(context);
        return sInstance;
    }

    @Override
    public User get(long id) {
        return mDAO.get(id);
    }

    public User get(String username) {
        return mDAO.get(username);
    }

    @Override
    public List<User> getList() {
        return mDAO.getList();
    }

    @Override
    public void insert(User user) {
        mDAO.insert(user);
    }

    @Override
    public void delete(User user) {
        mDAO.delete(user);
    }

    @Override
    public void update(User user) {
        mDAO.update(user);
    }

    public LiveData<List<UserWithTask>> getUserWithTask(){
            return mDAO.getUserWithTask();
    }

    public boolean checkExistUser(String username) {
        if (mDAO.get(username) == null)
            return false;
        return true;
    }

}
