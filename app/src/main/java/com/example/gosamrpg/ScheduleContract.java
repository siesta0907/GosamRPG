package com.example.gosamrpg;

import android.provider.BaseColumns;

/**
 * Created by 희수 on 2018-07-07.
 */

public final class ScheduleContract { //final 상속금지

    private ScheduleContract () { //private 인스턴스화 금지
    }

    public static class ScheduleEntry implements BaseColumns { //칼럼들을 정의함
        public static final String TABLE_NAME = "schedule";
        public static final String COLUMN_NAME_WORK = "work";
    }

}
