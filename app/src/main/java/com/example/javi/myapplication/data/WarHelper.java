package com.example.javi.myapplication.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by JAvi on 24/04/2018.
 */
//esta clase hereda de SQLiteOpenHelper
public class WarHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "war.db";
    private static final int DATABASE_VERSION = 1;
    //constructor heredado de SQLiteOpenHelper
    public WarHelper(Context context) {
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Crear la base de datos fisica war.db

        String SQL_CREATE_PERSONAJE_TABLE=
                "CREATE TABLE "+ ContratoWar.PersonajeEntry.TABLE_NAME+" ("
                        + ContratoWar.PersonajeEntry._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + ContratoWar.PersonajeEntry.COLUMN_PERSONAJE_NOMBRE+" TEXT NOT NULL, "
                        + ContratoWar.PersonajeEntry.COLUMN_PERSONAJE_VIDA+" INTEGER NOT NULL DEFAULT 100, "
                        + ContratoWar.PersonajeEntry.COLUMN_PERSONAJE_GENERO+" INTEGER NOT NULL, "
                        + ContratoWar.PersonajeEntry.COLUMN_PERSONAJE_DESCRIPCION+" TEXT );"
                ;
        //Ejecutar la consulta:
        db.execSQL(SQL_CREATE_PERSONAJE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
