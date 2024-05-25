package com.dreamk.newapp1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MySqliteOpenHelper extends SQLiteOpenHelper {

    private static SQLiteOpenHelper mInstance;
    public static synchronized SQLiteOpenHelper getmInstance(Context context){
        if(mInstance == null){
            mInstance = new MySqliteOpenHelper(context, "myDatabase1",null,1);
        }
        return mInstance;
    }

    private MySqliteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建表myTable1
        String sql = "create table myTable1("
                + "_id integer primary key autoincrement,"
                + "username text,"
                + " uNumber text,"
                + "uColor integer,"
                + "uMessage text,"
                + "permitted integer,"
                + "value1 integer)";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
