package com.animation_study.sqlit;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by css on 2017/12/4.
 * Step 1：自定义一个类继承SQLiteOpenHelper类
 * Step 2：在该类的构造方法的super中设置好要创建的数据库名,版本号
 * Step 3：重写onCreate( )方法创建表结构
 * Step 4：重写onUpgrade( )方法定义版本号发生改变后执行的操作
 */

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    private String foot = "create table person(" +
            "userid integer primary key autoincrement," +
            "name varchar(20)" +
            ")";
    private String update = "alter table person add phone varchar(12) null";

    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(foot);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(update);
    }
}
