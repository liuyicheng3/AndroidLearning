package com.lyc.study.go003;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by lyc on 2016/2/22.
 */
public class ContactUtil {

    public static HashMap<String, Double> getCallsInPhone(Activity act, HashMap<String, Double> weightBeans) {
        Cursor cursor = act.getContentResolver().query(CallLog.Calls.CONTENT_URI,
                new String[]{CallLog.Calls.DURATION, CallLog.Calls.TYPE, CallLog.Calls.DATE, CallLog.Calls.NUMBER},
                null,
                null,
                CallLog.Calls.DEFAULT_SORT_ORDER);
        act.startManagingCursor(cursor);
        boolean hasRecord = cursor.moveToFirst();
        long incoming = 0L;
        long outgoing = 0L;
        int count = 0;
        String strPhone = "";
        String date;
        while (hasRecord) {
            StringBuilder callBuilder = new StringBuilder();
            int type = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE));
            long duration = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DURATION));
            strPhone = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "yyyy-MM-dd hh:mm:ss");
            Date d = new Date(Long.parseLong(cursor.getString(cursor.getColumnIndex(CallLog.Calls.DATE))));
            date = dateFormat.format(d);
            callBuilder.append("电话:" + strPhone);
            callBuilder.append("        时间" + type);
            callBuilder.append("        时长" + duration);

            switch (type) {
                case CallLog.Calls.INCOMING_TYPE:
                    incoming += duration;
                    break;
                case CallLog.Calls.OUTGOING_TYPE:
                    outgoing += duration;
                default:

                    break;
            }
            count++;
            hasRecord = cursor.moveToNext();
            if (!weightBeans.containsKey(strPhone)){
                weightBeans.put(strPhone,Double.valueOf("1"));
            }else {
                double newWeight=weightBeans.get(strPhone)+1;
                weightBeans.put(strPhone,newWeight);
            }
        }
        return weightBeans;
    }

    public static ArrayList<ContactBean> getContact(Activity act,HashMap<String,Double> weightBeans) {
        ArrayList<ContactBean> contacts = new ArrayList<ContactBean>();
        // 获取数据库电话本的cursor
        ContentResolver cr = act.getContentResolver();
        // 取得电话本中开始一项的光标
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
                null, null, null);
        act.startManagingCursor(cur);

        while (cur.moveToNext()) {
            ContactBean sb= new ContactBean();
            sb.desplayName = cur.getString(cur
                    .getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
            // 获取联系人电话号码，处理多个号码的情况，不能直接使用PhoneLookup.NUMBER
            long id = cur.getLong(cur.getColumnIndex(ContactsContract.PhoneLookup._ID));
            Cursor numCur = cr.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "="
                            + Long.toString(id), null, null);

            while (numCur.moveToNext()) {
                String strPhoneNumber = numCur
                        .getString(numCur
                                .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                if (weightBeans.containsKey(strPhoneNumber))
                {
                    sb.weight+=weightBeans.get(strPhoneNumber);
                    weightBeans.remove(strPhoneNumber);
                }
                sb.phoneNumbers.add(strPhoneNumber);
            }
            numCur.close();

            //添加到map中，并将map添加到lstData

            contacts.add(sb);
        }
        return contacts;
    }
    public static HashMap<String,Double> getSmsInPhone(Activity act,HashMap<String,Double> weightBeans) {

        final String SMS_URI_ALL = "content://sms/";
        final String SMS_URI_INBOX = "content://sms/inbox";
        final String SMS_URI_SEND = "content://sms/sent";
        final String SMS_URI_DRAFT = "content://sms/draft";
        final String SMS_URI_OUTBOX = "content://sms/outbox";
        final String SMS_URI_FAILED = "content://sms/failed";
        final String SMS_URI_QUEUED = "content://sms/queued";


        try {
            Uri uri = Uri.parse(SMS_URI_ALL);
            String[] projection = new String[] { "_id", "address", "person", "body", "date", "type" };
            Cursor cur = act.getContentResolver().query(uri, projection, null, null, "date desc");      // 获取手机内部短信

            if (cur.moveToFirst()) {
                StringBuilder smsBuilder = new StringBuilder();
                int index_Address = cur.getColumnIndex("address");//手机号
                int index_Person = cur.getColumnIndex("person");
                int index_Body = cur.getColumnIndex("body");
                int index_Date = cur.getColumnIndex("date");
                int index_Type = cur.getColumnIndex("type");

                do {
                    String strAddress = cur.getString(index_Address);
                    int intPerson = cur.getInt(index_Person);
                    String strbody = cur.getString(index_Body);
                    long longDate = cur.getLong(index_Date);
                    int intType = cur.getInt(index_Type);

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    Date d = new Date(longDate);
                    String strDate = dateFormat.format(d);

                    String strType = "";
                    if (intType == 1) {
                        strType = "接收";
                    } else if (intType == 2) {
                        strType = "发送";
                    } else {
                        strType = "null";
                    }

                    smsBuilder.append("[ ");
                    smsBuilder.append(strAddress + ", ");
                    smsBuilder.append(intPerson + ", ");
                    smsBuilder.append(strbody + ", ");
                    smsBuilder.append(strDate + ", ");
                    smsBuilder.append(strType);
                    smsBuilder.append(" ]\n\n");
                    if (!weightBeans.containsKey(strAddress)){
                        weightBeans.put(strAddress,Double.valueOf("1"));
                    }else {
                        double newWeight=weightBeans.get(strAddress)+1;
                        weightBeans.put(strAddress,newWeight);
                    }
                } while (cur.moveToNext());

                if (!cur.isClosed()) {
                    cur.close();
                    cur = null;
                }
            } else {
                Log.d("sms","no result!");
            }

            Log.d("sms","getSmsInPhone has executed!");

        } catch (SQLiteException ex) {
            Log.d("sms","SQLiteException in getSmsInPhone");
        }

        return weightBeans;
    }

}
