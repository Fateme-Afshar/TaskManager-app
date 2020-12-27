package com.example.taskmaneger.data.room;

import androidx.room.TypeConverter;

import com.example.taskmaneger.model.TaskState;

import java.util.Date;
import java.util.UUID;

public class Converter {

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @androidx.room.TypeConverter
    public static String taskStateToStr(TaskState taskState) {
        return taskState.toString();
    }

    @androidx.room.TypeConverter
    public static TaskState strToTaskState(String str) {
        switch (str) {
            case "TODO":
                return TaskState.TODO;
            case "DONE":
                return TaskState.DONE;
            case "DOING":
                return TaskState.DOING;
            default:
                return null;
        }
    }
}
