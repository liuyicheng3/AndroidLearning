package com.lyc.study.go010;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by lyc on 17/3/25.
 */

public class RemarkInfoTable  extends BaseTable {

    public static  String table_name="RemarkInfoTable";


    public static  class Columns {
        public static String _id="_ID";
        public static String msg_id="MSG_ID";
        public static String remark_info="REMARK_INFO";
        public static String time="TIME";

    }



    public static  String[] all_collomn={
        Columns._id, Columns.msg_id, Columns.remark_info, Columns.time
    };

    @Override
    public String getName() {
        return table_name;
    }

    @Override
    public HashMap<String, String> getColumnsMap() {

        HashMap<String,String>  map=new LinkedHashMap<>();
        map.put(Columns._id," INTEGER PRIMARY KEY AUTOINCREMENT");
        map.put(Columns.msg_id," TEXT NOT NULL");
        map.put(Columns.remark_info," TEXT ");
        map.put(Columns.time," LONG ");
        return map;
    }

    private static RemarkInfoTable  INSTANCE;

    public static RemarkInfoTable getINSTANCE(){
        if (INSTANCE ==null){
            INSTANCE =new RemarkInfoTable();
        }
        return INSTANCE;
    }


    @Override
    public String getUniqueConstraint() {
        return "UNIQUE "+" ( "+ Columns.msg_id+" ) ";
    }

    @Override
    public ArrayList<String> getTrigger() {
        return null;
    }

    @Override
    public void upgradeTable(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
