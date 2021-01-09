package com.example.taskmaneger.data.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.taskmaneger.model.Task;

import java.util.List;
import java.util.UUID;

@Dao
public interface TaskTableDAO {
    @Query(value = "SELECT * FROM taskTable")
    LiveData<List<Task>> getList();
    @Query(value = "SELECT * FROM taskTable WHERE userId=:userId")
    LiveData<List<Task>> getListWithUserId(long userId);
    @Query(value = "SELECT * FROM taskTable WHERE state=:taskSate AND userId=:userId")
    LiveData<List<Task>> getListWithState(String taskSate,long userId);
    @Query(value = "SELECT * FROM taskTable  WHERE id=:taskId")
    LiveData<Task> get(long taskId);
    @Delete
    void delete(Task element);

    @Query(value = "DELETE FROM taskTable WHERE userId=:userId")
    void deleteAll(long userId);

    @Insert
    void insert(Task element);
    @Update
    void update(Task element);
}
