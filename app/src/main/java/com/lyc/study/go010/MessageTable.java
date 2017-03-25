package com.lyc.study.go010;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lyc.common.Mlog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by lyc on 17/3/25.
 */

public class MessageTable extends BaseTable {

    private static MessageTable  INSTANCE;

    public static MessageTable getINSTANCE(){
        if (INSTANCE ==null){
            INSTANCE =new MessageTable();
        }
        return INSTANCE;
    }


    public static String table_name="MessageTable";



    public static class Columns{
        public static String column_id="_ID";
        public static String column_msg_id="MSG_ID";
        public static String column_msg_content="CONTENT";
        public static String column_time="TIME";
    }

    public static  String[] ALL_Columns ={Columns.column_id,Columns.column_msg_id,Columns.column_msg_content,Columns.column_time};


    @Override
    public String getName() {
        return table_name;
    }

    @Override
    public HashMap<String, String> getColumnsMap() {
        HashMap<String,String> map =new LinkedHashMap<>();
        map.put(Columns.column_id," INTEGER PRIMARY KEY AUTOINCREMENT ");
        map.put(Columns.column_msg_id," TEXT NOT NULL ");
        map.put(Columns.column_msg_content," TEXT ");
        map.put(Columns.column_time," LONG ");
        return map;
    }

    @Override
    public String getUniqueConstraint() {
        return  "UNIQUE (" + MessageTable.Columns.column_msg_id + ") ON CONFLICT REPLACE";
    }

    /**
     * CREATE  TRIGGER trigger_name [BEFORE|AFTER] UPDATE OF column_name
     ON table_name
     BEGIN
     -- Trigger logic goes here....
     END;
     * @return
     *
     * 最开始时候我把所有trigger  卸载一起  然后execSQL  发现只有第一个trigger 创建成果了，后面几个全部眉头创建成果
     *
     * 然后试着 导出数据库 通过sqlite professional  调试才发现 需要一个一个trigger  单独创建才行
     */

    @Override
    public ArrayList<String> getTrigger() {
        ArrayList<String> triggers=new ArrayList<>();

        StringBuilder sb=new StringBuilder();


        sb.append("CREATE TRIGGER BROTHER_TRIGGER AFTER INSERT "+" ON "+table_name+" ");
        sb.append("BEGIN  ");
        sb.append("INSERT INTO "+RemarkInfoTable.table_name+" ("+RemarkInfoTable.Columns.msg_id+","+RemarkInfoTable.Columns.remark_info+","+RemarkInfoTable.Columns.time
                +") VALUES ( "+" new."+Columns.column_msg_id+" , new."+Columns.column_msg_content+" , new."+Columns.column_time+"); ");
        sb.append("END ;");
        triggers.add(sb.toString());
        sb.setLength(0);

        sb.append("CREATE TRIGGER DELETE_TRIGGER AFTER DELETE "+" ON "+table_name+" ");
        sb.append("BEGIN  ");
        sb.append("DELETE FROM  "+RemarkInfoTable.table_name+" WHERE "+RemarkInfoTable.Columns.msg_id+" like old."+Columns.column_msg_id +" ; ");
        sb.append("END ;");
        triggers.add(sb.toString());
        sb.setLength(0);

        sb.append("CREATE TRIGGER UPDATE_TRIGGER AFTER UPDATE "+" ON "+table_name+" ");
        sb.append("BEGIN  ");
        sb.append("UPDATE  "+RemarkInfoTable.table_name+" SET "+RemarkInfoTable.Columns.remark_info+" = new."+Columns.column_msg_content +
                " WHERE "+ RemarkInfoTable.Columns.msg_id+"  like old."+Columns.column_msg_id+" ; ");
        sb.append("END ;");
        triggers.add(sb.toString());
        sb.setLength(0);

        return triggers;
    }

    @Override
    public void upgradeTable(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public static  void save(SQLiteDatabase  db,String id,String content){
        ContentValues cvs=new ContentValues();
        cvs.put(Columns.column_msg_id,id);
        cvs.put(Columns.column_msg_content,content);
        cvs.put(Columns.column_time,System.currentTimeMillis()+"");
        db.insert(table_name,null,cvs);
    }

    public static Cursor read(SQLiteDatabase  db){
        return db.query(table_name, ALL_Columns,null,null,null,null,null);
    }

    public static  void update(SQLiteDatabase  db,int dataID,String id,String content){
        ContentValues cvs=new ContentValues();
        cvs.put(Columns.column_msg_id,id);
        cvs.put(Columns.column_msg_content,content);
        cvs.put(Columns.column_time,System.currentTimeMillis()+"");
        db.update(table_name,cvs,Columns.column_id+" =?",new String[]{dataID+""});
    }
    public static  void delete(SQLiteDatabase  db,int dataID){
        db.delete(table_name,Columns.column_id+" =?",new String[]{dataID+""});
    }
}
