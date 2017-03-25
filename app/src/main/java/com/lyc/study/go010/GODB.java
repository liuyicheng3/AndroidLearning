package com.lyc.study.go010;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lyc on 17/3/25.
 */

public class GODB extends SQLiteOpenHelper {


    private final static String Name = "GoDB";
    private final static int version = 2;


    private static GODB DB_INSTANCE;
    private static SQLiteDatabase db;

    public static SQLiteOpenHelper getDbInstance(Context ctx) {

        if (DB_INSTANCE == null) {
            DB_INSTANCE = new GODB(ctx);
        }

        return DB_INSTANCE;
    }


    public SQLiteDatabase getDataBase() {
        if (db == null) {
            db = getWritableDatabase();
        }
        return db;
    }


    private GODB(Context context) {
        super(context, Name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        MessageTable.getINSTANCE().createTable(db);
        RemarkInfoTable.getINSTANCE().createTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        MessageTable.getINSTANCE().upgradeTable(db, oldVersion, newVersion);

    }
}


