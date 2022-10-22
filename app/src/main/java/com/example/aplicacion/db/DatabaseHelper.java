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


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
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
                "ruc TEXT NOT NULL," +
                "nombre TEXT NOT NULL," +
                "apellido TEXT NOT NULL," +
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
