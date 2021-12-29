package com.example.gosamrpg;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 희수 on 2018-07-07.
 */

public class ScheduleDbHelper extends SQLiteOpenHelper {
    private static ScheduleDbHelper sInstance;

    private static final int DB_VERSION = 1; //db 버전이 1부터 시작함

    private static final String DB_NAME = "Schedule.db";

    private static final String SQL_CREATE_ENTRIES =  //테이블 생성
            String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT )",
                    ScheduleContract.ScheduleEntry.TABLE_NAME,
                    ScheduleContract.ScheduleEntry._ID,
                    ScheduleContract.ScheduleEntry.COLUMN_NAME_WORK);

    private final String SQL_DELETE_ENTRIES = //테이블 삭제
            "DROP TABLE IF EXISTS " + ScheduleContract.ScheduleEntry.TABLE_NAME;

    public static synchronized ScheduleDbHelper getInstance (Context context) { //여기서 이미 생성된 인스턴스를 얻음
        if (sInstance == null) {
            sInstance =  new ScheduleDbHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    private ScheduleDbHelper (Context context) {
        super(context, DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate (SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oidVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

}
