package com.example.taskmaneger.data.room;

import androidx.room.TypeConverter;

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

    @TypeConverter
    public static UUID strToUUID(String uuid) {
        return UUID.fromString(uuid);
    }

    @TypeConverter
    public static String uuidToStr(UUID uuid) {
        return uuid.toString();
    }

}
