package com.example.taskmaneger.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.taskmaneger.data.TaskManagerSchema;
import com.example.taskmaneger.data.TaskManagerSchema.User.UserColumns;

import java.util.Date;

@Entity(tableName = TaskManagerSchema.User.NAME)
public class User {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = UserColumns.ID)
    private long mId;
    @ColumnInfo(name = UserColumns.USERNAME)
    private String mUsername;
    @ColumnInfo(name = UserColumns.PASSWORD)
    private String mPassword;
    @ColumnInfo(name = UserColumns.IS_ADMIN)
    private boolean isAdmin;
    @ColumnInfo(name = UserColumns.MEMBERSHIP)
    private Date mMembershipDate;

    public User() {

    }

    public User(String username, String password, boolean isAdmin, Date membershipDate) {
        mUsername = username;
        mPassword = password;
        this.isAdmin = isAdmin;
        mMembershipDate = membershipDate;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public Date getMembershipDate() {
        return mMembershipDate;
    }

    public void setMembershipDate(Date membershipDate) {
        mMembershipDate = membershipDate;
    }
}
