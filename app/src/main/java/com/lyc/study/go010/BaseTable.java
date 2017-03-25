package com.lyc.study.go010;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by lyc on 17/3/25.
 */

public abstract class BaseTable {




    public abstract String getName();
    public abstract HashMap<String,String> getColumnsMap();
    public abstract String getUniqueConstraint() ;
    public abstract ArrayList<String> getTrigger() ;

        public abstract void upgradeTable(SQLiteDatabase db, int oldVersion, int newVersion);

    public  void createTable(SQLiteDatabase  db){


        StringBuilder sb =new StringBuilder() ;
        sb.append("CREATE TABLE IF NOT EXISTS "+ getName());
        sb.append(" ( ");
        Set<String> keySet=getColumnsMap().keySet();
        for (String cloum: keySet){
            sb.append(cloum+" "+getColumnsMap().get(cloum)+" ,");
        }
        sb.append(getUniqueConstraint());
        sb.append(" )");
        db.execSQL(sb.toString());
        if (getTrigger()!=null&&getTrigger().size()>0){
            for (String trigger:getTrigger()){
                db.execSQL(trigger);
            }

        }


    }


}
