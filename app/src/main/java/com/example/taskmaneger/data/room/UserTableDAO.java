package com.example.taskmaneger.data.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.taskmaneger.data.UserWithTask;
import com.example.taskmaneger.model.User;

import java.util.List;
import java.util.UUID;

@Dao
public interface UserTableDAO {

    @Query(value = "SELECT * FROM userTable")
    List<User> getList();
    @Query(value = "SELECT * FROM userTable WHERE id=:id")
    User get(long id);
    @Query(value = "SELECT * FROM userTable WHERE username=:username")
    User get(String username);

    @Transaction
    @Query(value = "SELECT * FROM userTable")
    UserWithTask getTasks();

    @Delete
    void delete(User user);
    @Insert
    void insert(User user);
    @Update
    void update(User user);
}
