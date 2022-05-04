package lk.ac.mrt.cse.dbs.simpleexpensemanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "ExpManager.db";
    public static final String TABLE_NAME_1 = "Account";
    public static final String TABLE_NAME_2 = "Log";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME_1 + "( Account_no varchar(10) primary key, Bank varchar(255), Acc_holder varchar(255), Balance real)");
        db.execSQL("create table " + TABLE_NAME_2 + "( Date varchar(20), Account_no varchar(10), Type varchar(20),  Amount real)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
    }
}
