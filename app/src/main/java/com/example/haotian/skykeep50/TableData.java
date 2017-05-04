package com.example.haotian.skykeep50;

import android.provider.BaseColumns;

/**
 * Created by xuehaotian on 20/03/2016.
 */

/**Author:HaotianXue u5689296**/

public class TableData {
    public TableData(){}

    public static abstract class TableInfo implements BaseColumns{
        public static final String CONTENT = "content";
        public static final String DATABSE_NAME = "user_info";
        public static final String TABLE_NAME = "not_info";
        public static final String ID = "rowid";

    }

}
