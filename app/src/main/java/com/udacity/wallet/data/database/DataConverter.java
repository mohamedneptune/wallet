package com.udacity.wallet.data.database;

import android.arch.persistence.room.TypeConverter;

import java.io.Serializable;
import java.sql.Date;


public class DataConverter implements Serializable {
    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

}
