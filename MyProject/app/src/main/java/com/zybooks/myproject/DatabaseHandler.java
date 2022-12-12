package com.zybooks.myproject;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "inventoryApp.db";
    private static final int VERSION = 2;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    private static final class InventoryLoginTable {
        private static final String TABLE = "credentials";
        private static final String COL_ID = "_id";
        private static final String COL_USERNAME = "username";
        private static final String COL_PASSWORD = "password";
    }

    private static final class InventoryItemsTable {
        private static final String TABLE = "inventory";
        private static final String COL_ID = "_id";
        private static final String COL_ITEM_NAME = "item";
        private static final String COL_QUANTITY = "quantity";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + InventoryLoginTable.TABLE + " (" +
                InventoryLoginTable.COL_ID + " integer primary key autoincrement, " +
                InventoryLoginTable.COL_USERNAME + " text,  " +
                InventoryLoginTable.COL_PASSWORD + " text)");

        db.execSQL("create table " + InventoryItemsTable.TABLE + " (" +
                InventoryItemsTable.COL_ID + " integer primary key autoincrement, " +
                InventoryItemsTable.COL_ITEM_NAME + " text, " +
                InventoryItemsTable.COL_QUANTITY + " integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + InventoryLoginTable.TABLE);
        db.execSQL("drop table if exists " + InventoryItemsTable.TABLE);
        onCreate(db);
    }

    //============================ functions for the login table ===================================

    //method to add new credentials into database
    public long addNewUserCredentials(String username, String password) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(InventoryLoginTable.COL_USERNAME, username);
        values.put(InventoryLoginTable.COL_PASSWORD, password);

        long credentialInsertId = db.insert(InventoryLoginTable.TABLE, null, values);
        db.close();
        return credentialInsertId;
    }

    //method to check if a user's submitted credentials exist
    //function borrowed from https://stackoverflow.com/questions/35882752/how-to-create-a-login-database-in-android
    public boolean checkCredential(String user, String pass) {
        SQLiteDatabase db = getReadableDatabase();

        String sql = "select username from " + InventoryLoginTable.TABLE + " where username = ? AND password = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{user, pass});

        //checks if the query returned a valid result
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                return true;
            }
        }
        return false;
    }

    //============================ end of functions for the login table ============================

    //function to add a new item to the inventory database
    //returns -1 if failed
    public long addItem(String userItem, String amount) {
        SQLiteDatabase db = getWritableDatabase();

        //adds new item using COL_ITEM_NAME and COL_QUANTITY
        ContentValues values = new ContentValues();
        values.put(InventoryItemsTable.COL_ITEM_NAME, userItem);
        values.put(InventoryItemsTable.COL_QUANTITY, amount);

        long credentialInsertId = db.insert(InventoryItemsTable.TABLE, null, values);
        db.close();
        return credentialInsertId;
    }

    //function to completely delete an item from the database
    public void deleteItem(int itemId) {
        String itemIdString = Integer.toString(itemId);
        SQLiteDatabase db = getWritableDatabase();

        db.delete(InventoryItemsTable.TABLE, "_id=?", new String[]{itemIdString});
        db.close();
    }

    public void deleteAllItems() {
        SQLiteDatabase db = getWritableDatabase();

        //db.delete(InventoryItemsTable.TABLE, null, null);
        db.execSQL("delete from " + InventoryItemsTable.TABLE);
        db.close();
    }

    //function to change an item's quantity
    public void updateItem(int itemId, int itemAmount) {
        SQLiteDatabase db = getWritableDatabase();

        //new item amount
        ContentValues values = new ContentValues();
        values.put(InventoryItemsTable.COL_QUANTITY, itemAmount);

        db.update(InventoryItemsTable.TABLE, values, "_id = ?", new String[]{String.valueOf(itemId)});
        db.close();
    }

    //function to retrieve an item from the database
    public void getItem(int itemId) {

        SQLiteDatabase db = getReadableDatabase();

        String sql = "select " + InventoryItemsTable.COL_QUANTITY + " from " + InventoryLoginTable.TABLE + " where quantity = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(itemId)});

        db.close();
    }

    //function to return all rows from table into cursor form
    public Cursor getAllRows(){
        SQLiteDatabase db = getReadableDatabase();
        String sql = "select * from " + InventoryItemsTable.TABLE;
        Cursor cursor = db.rawQuery(sql, null);
        return cursor;
    }

}
