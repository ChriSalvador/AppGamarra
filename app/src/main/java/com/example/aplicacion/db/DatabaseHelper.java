package com.example.aplicacion.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NOMBRE = "geoapp.db";

    public static final String TABLE_TIENDA = "t_tienda";
    public static final String TABLE_PRENDA = "t_prenda";
    public static final String TABLE_PUBLICO = "t_publico";
    public static final String TABLE_ZONA = "t_zona";


    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_PRENDA + "(" +
                "idprenda INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombreprenda TEXT NOT NULL)");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_PUBLICO + "(" +
                "idpublico INTEGER PRIMARY KEY AUTOINCREMENT," +
                "descripcion TEXT NOT NULL)");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_ZONA + "(" +
                "idzona INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombrezona TEXT NOT NULL)");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_TIENDA + "(" +
                "idtienda INTEGER PRIMARY KEY AUTOINCREMENT," +
                "idprenda INTEGER REFERENCES " + TABLE_PRENDA + "(idprenda)," +
                "idpublico INTENGER REFERENCES " + TABLE_PUBLICO + "(idpublico)," +
                "idzona INTENGER REFERENCES " + TABLE_ZONA + "(idzona)," +
                "razonsocial TEXT NOT NULL," +
                "ruc TEXT NOT NULL," +
                "propietario TEXT NOT NULL," +
                "direccion TEXT NOT NULL," +
                "referencia TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_PRENDA);
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_PUBLICO);
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_ZONA);
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_TIENDA);
        onCreate(sqLiteDatabase);
    }
}
