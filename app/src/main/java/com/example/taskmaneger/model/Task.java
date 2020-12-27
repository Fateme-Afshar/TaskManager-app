package com.example.taskmaneger.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.taskmaneger.data.TaskManagerSchema;
import com.example.taskmaneger.data.TaskManagerSchema.Task.TaskColumns;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = TaskManagerSchema.Task.NAME)
public class Task implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = TaskColumns.ID)
    private long mId;
    @ColumnInfo(name = TaskColumns.TITLE)
    private String mTaskTitle;
    @ColumnInfo(name = TaskColumns.CONTENT)
    private String mTaskContent;
    @ColumnInfo(name = TaskColumns.STATE)
    private TaskState mTaskState;
    @ColumnInfo(name = TaskColumns.DATE)
    private Date mTaskDate;
    @ColumnInfo(name = TaskColumns.TIME)
    private Date mTaskTime;
    @ColumnInfo(name = TaskColumns.USER_ID)
    private long mUserId;
    @ColumnInfo(name = TaskColumns.IMAGE_ADDRESS)
    private String mImgAddress;

    public Task(long userId,
                String taskTitle,
                String taskContent,
                TaskState taskState,
                Date taskDate,
                Date taskTime,
                String imgAddress) {
        mTaskTitle = taskTitle;
        mTaskContent = taskContent;
        mTaskState = taskState;
        mTaskDate = taskDate;
        mTaskTime = taskTime;
        mUserId = userId;
        mImgAddress = imgAddress;
    }

    public String getTaskTitle() {
        return mTaskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        mTaskTitle = taskTitle;
    }

    public String getTaskContent() {
        return mTaskContent;
    }

    public void setTaskContent(String taskContent) {
        mTaskContent = taskContent;
    }

    public TaskState getTaskState() {
        return mTaskState;
    }

    public void setTaskState(TaskState taskState) {
        mTaskState = taskState;
    }

    public Date getTaskDate() {
        return mTaskDate;
    }

    public void setTaskDate(Date taskDate) {
        mTaskDate = taskDate;
    }

    public Date getTaskTime() {
        return mTaskTime;
    }

    public void setTaskTime(Date taskTime) {
        mTaskTime = taskTime;
    }

    public long getUserId() {
        return mUserId;
    }

    public void setUserId(long userId) {
        mUserId = userId;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }


    public String getImgAddress() {
        return mImgAddress;
    }

    public void setImgAddress(String imgPath) {
        mImgAddress = imgPath;
    }
}
