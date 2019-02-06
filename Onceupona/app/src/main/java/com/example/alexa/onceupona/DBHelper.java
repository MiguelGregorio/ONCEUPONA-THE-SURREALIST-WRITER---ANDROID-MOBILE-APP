package com.example.alexa.onceupona;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.text.SpannableString;


public class DBHelper extends SQLiteOpenHelper {
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS ONCEUPONA(ID INTEGER PRIMARY KEY AUTOINCREMENT, DATA VARCHAR(50), TEXTO VARCHAR(500))");
    }

    public DBHelper(Context context) {
        super(context, "Onceupona.db", null, 1);
    }

    //insere um novo elemento na BD
    public void insertData(String data, String texto){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "INSERT INTO ONCEUPONA VALUES(NULL,?,?)";

        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1,data);
        statement.bindString(2,texto);

        statement.executeInsert();
    }



    //devolve os dados da BD
    public Cursor getData(String sql){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(sql,null);
    }

    //Apaga os dados dum elemento com um determinado ID
    public void deleteData(int ID){
        SQLiteDatabase db = getWritableDatabase();

        String sql = "DELETE FROM ONCEUPONA WHERE ID = ?";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, (double)ID);

        statement.execute();
        db.close();
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
