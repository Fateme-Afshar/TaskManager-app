package com.example.taskmaneger.data.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.taskmaneger.model.Task;
import com.example.taskmaneger.model.User;

@Database(entities = {User.class, Task.class}, version = 1)
@TypeConverters({Converter.class})
public abstract class TaskManagerDatabase extends RoomDatabase {

    public abstract UserTableDAO getUserDao();
    public abstract TaskTableDAO getTaskDao();
}
