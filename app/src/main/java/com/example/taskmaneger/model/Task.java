package com.example.taskmaneger.model;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.bumptech.glide.Glide;
import com.example.taskmaneger.R;
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
    private String mTitle;
    @ColumnInfo(name = TaskColumns.CONTENT)
    private String mDescription;
    @ColumnInfo(name = TaskColumns.STATE)
    private TaskState mTaskState;
    @ColumnInfo(name = TaskColumns.DATE)
    private Date mDate;
    @ColumnInfo(name = TaskColumns.TIME)
    private Date mTime;
    @ColumnInfo(name = TaskColumns.USER_ID)
    private long mUserId;
    @ColumnInfo(name = TaskColumns.IMAGE_ADDRESS)
    private String mImgAddress;

    public Task() {
        mDate=new Date();
        mTime=new Date();
    }

    public Task(long userId,
                String title,
                String description,
                TaskState taskState,
                Date date,
                Date time,
                String imgAddress) {
        mTitle = title;
        mDescription = description;
        mTaskState = taskState;
        mDate = date;
        mTime = time;
        mUserId = userId;
        mImgAddress = imgAddress;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public TaskState getTaskState() {
        return mTaskState;
    }

    public void setTaskState(TaskState taskState) {
        mTaskState = taskState;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public Date getTime() {
        return mTime;
    }

    public void setTime(Date time) {
        mTime = time;
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

    @BindingAdapter("loadImage")
    public static void loadImage(ImageView imageView,String imgAddress){
        Glide.with(imageView.getContext()).
                load(imgAddress).
                placeholder(R.drawable.img_place_holder).
                circleCrop().
                into(imageView);
    }
}
