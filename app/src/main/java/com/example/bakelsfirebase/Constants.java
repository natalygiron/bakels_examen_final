package com.example.bakelsfirebase;

public class Constants {
    // nombre base de datos
    public static final String DB_NAME = "My_RECORDS_DB";
    //version de base de datos
    public static final int DB_VERSION = 1;
    //nombre de la tabla
    public static final String TABLE_NAME = "MY_RECORDS_TABLE";
    //columnas/campos de la tabla
    public static final String C_ID = "ID";
    public static final String C_NOMBRE = "NOMBRE";
    public static  final String C_IMAGE = "IMAGE";
    public static final String C_MARCA = "MARCA";
    public static final String C_CATEGORIA = "CATEGORIA";
    public static final String C_PRECIO = "PRECIO";
    public static final String C_STOCK = "STOCK";
    public static final String C_ADDED_TIMESTAMP = "ADDED_TIME_STAMP";
    public static final String C_UPDATED_TIMESTAMP = "UPDATED_TIME_STAMP";
    //Crea la tabla Query
    public static  final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +"("
            + C_ID+ " INTEGER PRIMARY KEY,"
            + C_NOMBRE+ " TEXT,"
            + C_IMAGE+ " TEXT,"
            + C_MARCA+ " TEXT,"
            + C_CATEGORIA+ " TEXT,"
            + C_PRECIO+ " TEXT,"
            + C_STOCK+ " TEXT,"
            + C_ADDED_TIMESTAMP+ " TEXT,"
            + C_UPDATED_TIMESTAMP+ " TEXT"
            + ")";
}
