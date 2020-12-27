package com.example.taskmaneger.data.room;

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
    List<Task> getList();
    @Query(value = "SELECT * FROM taskTable WHERE userId=:userId")
    List<Task> getListWithUserId(long userId);
    @Query(value = "SELECT * FROM taskTable WHERE state=:taskSate AND userId=:userId")
    List<Task> getListWithState(String taskSate,long userId);
    @Query(value = "SELECT * FROM taskTable  WHERE id=:taskId")
    Task get(long taskId);
    @Delete
    void delete(Task element);
    @Insert
    void insert(Task element);
    @Update
    void update(Task element);
}
