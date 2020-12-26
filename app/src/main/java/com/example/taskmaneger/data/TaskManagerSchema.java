package com.example.taskmaneger.data;

public final class TaskManagerSchema {
    public static final String NAME="task_manager.db";
    public static final int VERSION=1;

    public static final class User{
        public static final String NAME="userTable";

        public static final class UserColumns{
            public static final String ID="id";
            public static final String UUID="uuid";
            public static final String USERNAME="username";
            public static final String PASSWORD="password";
            public static final String MEMBERSHIP="membership";
            public static final String ISADMIN="isAdmin";
        }
    }
}
