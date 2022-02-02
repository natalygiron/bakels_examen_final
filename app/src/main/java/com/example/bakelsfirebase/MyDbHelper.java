package com.example.bakelsfirebase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

// Clase DataBase Helper que contiene todos los métodos crud
public class MyDbHelper extends SQLiteOpenHelper {

    public MyDbHelper(@Nullable Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Crea la tabla de la base de datos
        db.execSQL(Constants.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // actualizar la base de datos (si hay alguna estructura, cambie la versión de db)

        //descartar la tabla anterior si existe
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);
        //crear tabla de nuevo
        onCreate(db);
    }

    //Inserta datos a la base de datos
    public long insertRecord(String nombre, String image, String marca, String categoria,
                             String precio, String stock, String addedTime, String updatedTime) {

        //get databse grabable porque queremos escribir datos

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();


        // la identificación se insertará automáticamente cuando configuremos AUTOINCREMENTO en la consulta

        // inserta datos
        values.put(Constants.C_NOMBRE, nombre);
        values.put(Constants.C_IMAGE, image);
        values.put(Constants.C_MARCA, marca);
        values.put(Constants.C_CATEGORIA, categoria);
        values.put(Constants.C_PRECIO, precio);
        values.put(Constants.C_STOCK, stock);
        values.put(Constants.C_ADDED_TIMESTAMP, addedTime);
        values.put(Constants.C_UPDATED_TIMESTAMP, updatedTime);

        // insertar fila, devolverá la identificación del registro guardado
        long id = db.insert(Constants.TABLE_NAME, null, values);


        // cerrar db Connection
        db.close();


        // devuelve la identificación del registro insertado
        return id;

    }

    //Obtener todos datos
    public ArrayList<ModelRecord> getAllRecords(String orderBy) {
        // la orden de consulta permitirá ordenar los datos más nuevo / más antiguo primero, nombre ascendente / descendente
        // devolverá la lista o registros ya que hemos utilizado return tipo ArrayList <ModelRecord>

        ArrayList<ModelRecord> recordsList = new ArrayList<>();
        // consulta para seleccionar registros
        String selectQuery = " SELECT * FROM " + Constants.TABLE_NAME + " ORDER BY " + orderBy;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // recorrer todos los registros y agregarlos a la lista
        if (cursor.moveToFirst()) {
            do {
                ModelRecord modelRecord = new ModelRecord(
                        "" + cursor.getInt(cursor.getColumnIndex(Constants.C_ID)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_NOMBRE)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_IMAGE)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_MARCA)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_CATEGORIA)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_PRECIO)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_STOCK)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_ADDED_TIMESTAMP)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_UPDATED_TIMESTAMP)));

                // Añadir registro a la list
                recordsList.add(modelRecord);
            } while (cursor.moveToNext());
        }

        //cierre de conexión db

        db.close();

        //retorna la lista
        return recordsList;
    }

    //Buscar todos datos
    public ArrayList<ModelRecord> searchRecords(String query) {
        // la orden de consulta permitirá ordenar los datos más nuevo / más antiguo primero, nombre ascendente / descendente
        // devolverá la lista o registros ya que hemos utilizado return tipo ArrayList <ModelRecord>

        ArrayList<ModelRecord> recordsList = new ArrayList<>();
        // consulta para seleccionar registros
        String selectQuery = " SELECT * FROM " + Constants.TABLE_NAME + " WHERE " + Constants.C_NOMBRE + " LIKE '%" + query + "%'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // recorrer todos los registros y agregarlos a la lista
        if (cursor.moveToFirst()) {
            do {
                ModelRecord modelRecord = new ModelRecord(
                        "" + cursor.getInt(cursor.getColumnIndex(Constants.C_ID)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_NOMBRE)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_IMAGE)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_MARCA)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_CATEGORIA)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_PRECIO)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_STOCK)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_ADDED_TIMESTAMP)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_UPDATED_TIMESTAMP)));

                // Añadir registro a la list
                recordsList.add(modelRecord);
            } while (cursor.moveToNext());
        }

        //cierre de conexión db

        db.close();

        //retorna la lista
        return recordsList;
    }

    //Obtener el numero de registros
    public int getRecordsCount() {
        String countQuery = " SELECT * FROM " + Constants.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        return count;
    }


}
