package com.example.sqliteopenhelper.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.sqliteopenhelper.model.User

class DatabaseHandler(context: Context): SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE = "CREATE TABLE $TABLE_NAME " +
                          "($ID Integer PRIMARY KEY, $FIRST_NAME TEXT, $LAST_NAME TEXT)"
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
    fun addUser(user: User): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(FIRST_NAME, user.firstName)
        values.put(LAST_NAME, user.lastName)
        val _success = db.insert(TABLE_NAME, null, values)
        db.close()
        Log.v("InsertedID", "$_success")
        return (Integer.parseInt("$_success") != -1)
    }
    fun getAllUsers(): String {
        var allUser: String = ""
        val db = readableDatabase
        val selectAllQuery = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectAllQuery, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    var id = cursor.getString(cursor.getColumnIndexOrThrow(ID))
                    var firstName = cursor.getString(cursor.getColumnIndexOrThrow(FIRST_NAME))
                    var lastName = cursor.getString(cursor.getColumnIndexOrThrow(LAST_NAME))

                    allUser = "$allUser\n$id $firstName $lastName"
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        db.close()
        return allUser
    }

    companion object {
        private val DB_NAME = "UserDB"
        private val DB_VERSION = 1;
        private val TABLE_NAME = "users"
        private val ID = "id"
        private val FIRST_NAME = "FirstName"
        private val LAST_NAME = "LastName"
    }
}