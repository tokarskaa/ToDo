package com.example.tokarska.todo

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder

class DatabaseHandler : SQLiteOpenHelper {

    companion object {

        val Tag = "DatabaseHandler"
        val DBName = "TaskDB"
        val DBVersion = 1

        val tableName = "taskTable"
        val Id = "id"
        val TaskContent = "content"
    }

    var context: Context? = null
    var sqlObj: SQLiteDatabase

    constructor(context: Context) : super(context, DBName, null, DBVersion) {

        this.context = context;
        sqlObj = this.writableDatabase;
    }
    override fun onCreate(p0: SQLiteDatabase?) {
        var sql1: String = "CREATE TABLE IF NOT EXISTS " + tableName + " " +
                "(" + Id + " INTEGER PRIMARY KEY," +
                TaskContent + " TEXT );"
        p0!!.execSQL(sql1);
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0!!.execSQL("Drop table IF EXISTS " + tableName)
        onCreate(p0)
    }

    fun addTask(values: ContentValues) : String {
        var Msg: String = "error";
        val ID = sqlObj!!.insert(tableName, "", values)

        if (ID > 0) {
            Msg = "ok"
        }
        return Msg
    }

    fun removeTask(id: Int): String {

        var selectionArs = arrayOf(id.toString())

        val i = sqlObj!!.delete(tableName, "id=?", selectionArs)
        if (i > 0) {
            return "ok"
        } else {

            return "error"
        }
    }

    fun fetchTasks(keyword: String): ArrayList<Task> {

        var arraylist = ArrayList<Task>()

        val sqb = SQLiteQueryBuilder()
        sqb.tables = tableName
        val cols = arrayOf("id", "content")
        val rowSelArg = arrayOf(keyword)

        val cur = sqb.query(sqlObj, cols, "content like ?", rowSelArg, null, null, "content")

        if (cur.moveToFirst()) {

            do {
                val id = cur.getInt(cur.getColumnIndex("id"))
                val content = cur.getString(cur.getColumnIndex("content"))

                arraylist.add(Task(content, id))

            } while (cur.moveToNext())
        }

        var count: Int = arraylist.size

        return arraylist
    }
}