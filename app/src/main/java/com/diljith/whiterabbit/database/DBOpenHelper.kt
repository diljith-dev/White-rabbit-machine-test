package com.diljith.whiterabbit.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.diljith.whiterabbit.utils.Utils
import com.diljith.whiterabbit.model.Employee


class DBOpenHelper(
    context: Context?,
    factory: SQLiteDatabase.CursorFactory?,
) :
    SQLiteOpenHelper(
        context, DATABASE_NAME,
        factory, DATABASE_VERSION
    ) {

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TABLE = ("CREATE TABLE " +
                EMPLOYEE_TABLE + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + id + " TEXT ,"
                + username + " TEXT ,"
                + name + " TEXT ,"

                + email + " TEXT ,"
                + profile_image + " TEXT ,"
                + phone + " TEXT ,"

                + website + " TEXT ,"
                + address + " TEXT ,"
                + company + " TEXT "
                + ")")
        db.execSQL(CREATE_TABLE)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

        db.execSQL("DROP TABLE IF EXISTS $EMPLOYEE_TABLE")
        onCreate(db)
    }

    fun insertToDoData(data: ArrayList<Employee>): Boolean {
        val v = ContentValues()
        val db = this.writableDatabase
        db.beginTransaction()

        try {
            for (e in data) {

                v.put(id, e.id)
                v.put(username, e.username)
                v.put(name, e.name)
                v.put(email, e.email)
                v.put(profile_image, e.profile_image)

                v.put(phone, e.phone)
                v.put(website, e.website)
                v.put(address, Utils().wrapData(e.address))
                v.put(company, Utils().wrapData(e.company))

                db.insert(EMPLOYEE_TABLE, null, v)
            }
            db.setTransactionSuccessful()
        } catch (e: SQLException) {
            e.printStackTrace()
            return false
        } finally {
            db.endTransaction()
        }

        try {
            db.close()
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

        db.close()
        return true
    }

    @SuppressLint("Range")
    fun getDetails(): ArrayList<Employee>? {
        val list = ArrayList<Employee>()
        try {
            val db = this.readableDatabase
            val cursor = db.rawQuery("SELECT * FROM $EMPLOYEE_TABLE", null)
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        val id = cursor.getString(cursor.getColumnIndex(id))
                        val username = cursor.getString(cursor.getColumnIndex(username))
                        val name = cursor.getString(cursor.getColumnIndex(name))
                        val email = cursor.getString(cursor.getColumnIndex(email))
                        val profile_image = cursor.getString(cursor.getColumnIndex(profile_image))
                        val phone = cursor.getString(cursor.getColumnIndex(Companion.phone))
                        val website = cursor.getString(cursor.getColumnIndex(website))
                        val address = cursor.getString(cursor.getColumnIndex(address))
                        val company = cursor.getString(cursor.getColumnIndex(company))

                        val modelList = Employee(Utils().getAddress(address),
                            Utils().getCompany(company),
                            email,
                            id,
                            name,
                            phone,
                            profile_image,
                            username,
                            website)

                        list.add(modelList)
                    } while (cursor.moveToNext())
                }
                cursor.close()
            }

        } catch (e: Exception) {
            e.stackTrace
        }
        return list
    }


    fun clearAllLocalTableData() {
        val db = this.writableDatabase
        db.execSQL("delete from $EMPLOYEE_TABLE")
        db.close()
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "employee_db.db"
        const val EMPLOYEE_TABLE = "employee_table"
        const val COLUMN_ID = "uniq_id"

        const val id = "id"
        const val username = "username"
        const val name = "name"
        const val email = "email"
        const val profile_image = "profile_image"

        const val phone = "phone"
        const val website = "website"
        const val address = "address"
        const val company = "company"
    }
}