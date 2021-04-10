package com.example.taskmaneger.data.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.taskmaneger.TaskManagerApplication;
import com.example.taskmaneger.data.TaskManagerSchema;
import com.example.taskmaneger.model.Task;
import com.example.taskmaneger.model.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, Task.class}, version = 1)
@TypeConverters({Converter.class})
public abstract class TaskManagerDatabase extends RoomDatabase {

    public abstract UserTableDAO getUserDao();
    public abstract TaskTableDAO getTaskDao();

    public static final int NUMBER_OF_THREADS = 4;
    public static ExecutorService databaseWriteExecutor=
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static TaskManagerDatabase getDatabase(){
        return Room.databaseBuilder
                (TaskManagerApplication.getApplicationGraph().getContext(),
                        TaskManagerDatabase.class,
                        TaskManagerSchema.NAME).build();
    }
}
