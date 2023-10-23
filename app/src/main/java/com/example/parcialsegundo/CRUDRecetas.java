package com.example.parcialsegundo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CRUDRecetas extends SQLiteOpenHelper {
    private static final String BD_NOMBRE = "myDatabase.db";
    private static final int BD_VERSION = 1;
    private static final String TABLA_SQL = "CREATE TABLE recetas " +
            "(id INTEGER PRIMARY KEY AUTOINCREMENT, titulo TEXT, descripcion TEXT)";

    public CRUDRecetas(Context context) {
        super(context, BD_NOMBRE, null, BD_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase bd) {
        bd.execSQL(TABLA_SQL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase bd, int i, int i1) {

    }

    public void insertarReceta(String titulo, String descripcion) {
        SQLiteDatabase bd = this.getWritableDatabase();
        ContentValues registros = new ContentValues();
        registros.put("titulo", titulo);
        registros.put("descripcion", descripcion);
        bd.insert("recetas", null, registros);
        bd.close();
    }

    public Cursor mostrarRecetas() {
        SQLiteDatabase bd = this.getReadableDatabase();
        String consultaSQL = "SELECT * FROM recetas";
        Cursor listaRegistros = bd.rawQuery(consultaSQL, null);
        return listaRegistros;
    }

    public void actualizarReceta(int id, String titulo, String descripcion) {
        SQLiteDatabase bd = this.getWritableDatabase();
        ContentValues registros = new ContentValues();
        registros.put("titulo", titulo);
        registros.put("descripcion", descripcion);
        bd.update("recetas", registros, "id=?", new String[]{String.valueOf(id)});
    }

    public void eliminarReceta(int id) {
        SQLiteDatabase bd = this.getWritableDatabase();
        bd.delete("recetas", "id=?", new String[]{String.valueOf(id)});
    }

}
