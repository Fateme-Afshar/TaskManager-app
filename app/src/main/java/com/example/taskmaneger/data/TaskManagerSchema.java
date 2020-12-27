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
            public static final String IS_ADMIN="isAdmin";
        }
    }

    public static final class Task{
        public static final String NAME="taskTable";

        public static final class TaskColumns {
            public static final String ID="id";
            public static final String TITLE="title";
            public static final String CONTENT="content";
            public static final String DATE="date";
            public static final String TIME="time";
            public static final String STATE="state";
            public static final String USER_ID="userId";
            public static final String IMAGE_ADDRESS="imageAddress";
        }
    }
}
