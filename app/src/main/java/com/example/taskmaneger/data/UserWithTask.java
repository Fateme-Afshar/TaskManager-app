package com.example.taskmaneger.data;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.taskmaneger.model.Task;
import com.example.taskmaneger.model.User;

import java.util.List;

public class UserWithTask {
    @Embedded public User mUser;

    @Relation(
            parentColumn = TaskManagerSchema.User.UserColumns.ID,
            entityColumn =TaskManagerSchema.Task.TaskColumns.USER_ID,
            entity = Task.class
    )
    public List<Task> mTaskList;

    public List<Task> getTaskList() {
        return mTaskList;
    }

    public void setTaskList(List<Task> taskList) {
        mTaskList = taskList;
    }
}
