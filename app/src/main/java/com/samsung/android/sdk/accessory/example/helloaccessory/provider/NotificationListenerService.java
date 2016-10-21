package com.samsung.android.sdk.accessory.example.helloaccessory.provider;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class NotificationListenerService extends android.service.notification.NotificationListenerService
{
    private MySQLiteOpenHelper helper;
    String dbName = "st_file.db";
    int dbVersion = 1; // 데이터베이스 버전
    private SQLiteDatabase db;
    String tag = "SQLite"; // Log 에 사용할 tag

    final String tableName = "mytable";

    @Override
    public void onCreate() {
        super.onCreate();

        // sqLite3 : 모바일 용으로 제작된 경량화 DB
        //         C언어로 엔진이 제작되어 가볍다
        // 안드로이드에서 sqLite3 를 쉽게 사용할 수 있도록 SQLiteOpenHelper클래스제공
        helper = new MySQLiteOpenHelper(
                this,  // 현재 화면의 제어권자
                dbName,// db 이름
                null,  // 커서팩토리-null : 표준커서가 사용됨
                dbVersion);       // 버전

        try {
//         // 데이터베이스 객체를 얻어오는 다른 간단한 방법
//         db = openOrCreateDatabase(dbName,  // 데이터베이스파일 이름
//                          Context.MODE_PRIVATE, // 파일 모드
//                          null);    // 커서 팩토리
//
//         String sql = "create table mytable(id integer primary key autoincrement, name text);";
//        db.execSQL(sql);

            db = helper.getWritableDatabase(); // 읽고 쓸수 있는 DB
            //db = helper.getReadableDatabase(); // 읽기 전용 DB select문
/*
            db.execSQL("delete from mytable2;");

            db.execSQL("insert into mytable2 (command, description, option) values('1','카톡보내기','2');");
            db.execSQL("insert into mytable2 (command, description, option) values('2','사진촬영','1');");
            db.execSQL("insert into mytable2 (command, description, option) values('3','플래시','0');");
*/
            Cursor c = db.rawQuery("select * from mytable2;", null);

            while (c.moveToNext()) {
                int id = c.getInt(0);
                String desc = c.getString(2);
                String option = c.getString(3);

                Log.d("DD", "ID : " + id + ", DESCRIPTION : " + desc + ", OPTION : " + option + "    [notificationservice]");
            }
        }catch (SQLiteException e) {
            e.printStackTrace();
            Log.e("DD", "데이터베이스를 얻어올 수 없음1");
        }
    }
    @Override
    public void onNotificationPosted(StatusBarNotification sbn)
    {
        if(sbn.getNotification().tickerText != null && sbn.getPackageName().equals("com.kakao.talk")) {
            //---show current notification---
            Log.i("DD", "---Current Notification---");
            Log.i("DD", "ID :" + sbn.getId() + "\t" +
                    sbn.getNotification().tickerText + "\t" +
                    sbn.getPackageName());
            Log.i("DD", "--------------------------");
/*
            //---show all active notifications---
            Log.i("DD", "===All Notifications===");
            for (StatusBarNotification notif : this.getActiveNotifications()) {
                Log.i("DD", "ID :" + notif.getId() + "\t" +
                        notif.getNotification().tickerText + "\t" +
                        notif.getPackageName());
            }
            Log.i("DD", "=======================");
*/
            Log.d("DD", "카카오 문자 DB삽입");
            db.execSQL("insert into mytable (name) values('" + sbn.getNotification().tickerText + "');");

            select();
        }
        else if(sbn.getNotification().tickerText != null && sbn.getPackageName().equals("jp.naver.line.android")) {
            //---show current notification---
            Log.i("DD", "---Current Notification---");
            Log.i("DD", "ID :" + sbn.getId() + "\t" +
                    sbn.getNotification().tickerText + "\t" +
                    sbn.getPackageName());
            Log.i("DD", "--------------------------");
/*
            //---show all active notifications---
            Log.i("DD", "===All Notifications===");
            for (StatusBarNotification notif : this.getActiveNotifications()) {
                Log.i("DD", "ID :" + notif.getId() + "\t" +
                        notif.getNotification().tickerText + "\t" +
                        notif.getPackageName());
            }
            Log.i("DD", "=======================");
*/
            Log.d("DD", "라인 문자 DB삽입");
            db.execSQL("insert into mytable3 (name) values('" + sbn.getNotification().tickerText + "');");

            select3();
        }
        else if(sbn.getNotification().tickerText != null && sbn.getPackageName().equals("com.facebook.katana")) {
            //---show current notification---
            Log.i("DD", "---Current Notification---");
            Log.i("DD", "ID :" + sbn.getId() + "\t" +
                    sbn.getNotification().tickerText + "\t" +
                    sbn.getPackageName());
            Log.i("DD", "--------------------------");
/*
            //---show all active notifications---
            Log.i("DD", "===All Notifications===");
            for (StatusBarNotification notif : this.getActiveNotifications()) {
                Log.i("DD", "ID :" + notif.getId() + "\t" +
                        notif.getNotification().tickerText + "\t" +
                        notif.getPackageName());
            }
            Log.i("DD", "=======================");
*/
            Log.d("DD", "페이스북 문자 DB삽입");
            db.execSQL("insert into mytable4 (name) values('" + sbn.getNotification().tickerText + "');");

            select4();
        }
    }

    void select() {
        Log.d("DD", "SELECT");
        Cursor c = db.rawQuery("select * from mytable;", null);
        while(c.moveToNext()) {
            int id = c.getInt(0);
            String name = c.getString(1);
            Log.d("DD","select : id:"+id+",name:"+name);
        }

        if(c.getCount() >= 30)
        {
            delete();
        }
    }

    void select3() {
        Log.d("DD","SELECT");
        Cursor c = db.rawQuery("select * from mytable3;", null);
        while(c.moveToNext()) {
            int id = c.getInt(0);
            String name = c.getString(1);
            Log.d("DD","select : id:"+id+",name:"+name);
        }

        if(c.getCount() >= 30)
        {
            delete3();
        }
    }

    void select4() {
        Log.d("DD","SELECT");
        Cursor c = db.rawQuery("select * from mytable4;", null);
        while(c.moveToNext()) {
            int id = c.getInt(0);
            String name = c.getString(1);
            Log.d("DD","select : id:"+id+",name:"+name);
        }

        if(c.getCount() >= 30)
        {
            delete4();
        }
    }

    void delete()
    {
        Log.d("DD","DB 삭제");
        db.execSQL("delete from mytable;");
    }

    void delete3()
    {
        Log.d("DD","DB 삭제");
        db.execSQL("delete from mytable;");
    }

    void delete4()
    {
        Log.d("DD","DB 삭제");
        db.execSQL("delete from mytable;");
    }
    @Override
    public void onNotificationRemoved(
            StatusBarNotification sbn) {
        Log.i("","---Notification Removed---");
        Log.i("","ID :" + sbn.getId() + "\t" +
                sbn.getNotification().tickerText + "\t" +
                sbn.getPackageName());
        Log.i("","--------------------------");
    }
}