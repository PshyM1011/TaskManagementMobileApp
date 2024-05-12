package com.example.taskmanagementapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class TasksDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME,null, DATABASE_VERSION) {

    companion object{
        private const val DATABASE_NAME = "task_management_app.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "tasks"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_DESCRIPTION = "description"

    }
//Implement both members of SQLite open Helper

    // create table using CREATE TABLE query
    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_TITLE TEXT, $COLUMN_DESCRIPTION TEXT)"
        db?.execSQL(createTableQuery)   //execute and run
    }

    //To remove table if similar table name exists
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)  //after deleting table, a new table will be created using onCreate method
    }

    // insert task function

    fun createTask(task: Task){
        val db = writableDatabase  //it can be modified
        val values = ContentValues().apply{
            put(COLUMN_TITLE, task.title)
            put(COLUMN_DESCRIPTION, task.description)
        }  //class that can store values associate with column names

        db.insert(TABLE_NAME, null, values)     //as we don't want an empty row put as null
        db.close()
        //id is provided by SQLite database as unique serial number

    }

    fun getAllTasks (): List<Task>{     //extends List of Tasks data class
        val tasksList = mutableListOf<Task>()   //empty tasks lists that can be modified or flexible
        val db = readableDatabase     //since we only need to read data
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query,null)     //to execute above query    // store results in cursor variable

        while(cursor.moveToNext()){      //go to next row     //we want to retrieve data as per index value therefore we do iteration row by row
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))  //retrieve value of column ID from current row in the cursor
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))

            val task = Task(id, title, description)  //after successful retrieval pass them as an argument and store it in task variable
            tasksList.add(task)   //add this stored task variable into taskslist
        }
        cursor.close()
        db.close()
        return tasksList   //act as a list which consist of all retrieved data

    }

    fun updateTask(task: Task){   //pass Task Data class as a task variable
        val db = writableDatabase
        val values = ContentValues().apply{   //store columns with data that need to be update
            put(COLUMN_TITLE, task.title)   //add title & description into respective column
            put(COLUMN_DESCRIPTION, task.description)
        }
        val whereClause = "$COLUMN_ID = ?"       //variable that will be used to identify rows to be updated by its column ID     //which row need to be update to given ID
        val whereArgs = arrayOf(task.id.toString())    // initialize an array containing argument that is Task ID
        db.update(TABLE_NAME, values, whereClause, whereArgs)
        db.close()
    }

    fun getTaskByID(taskId: Int): Task{
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $taskId"     //Get entre table row relevant to given TaskID
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()   //move cursor to the first row of the result

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))   //extract from respective column
        val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
        val description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))

        cursor.close()
        db.close()
        return Task(id, title, description)
    }

    fun deleteTask(taskId: Int){
        val db = writableDatabase    // as we are deleting record within database means database is modified
        val whereClause = "$COLUMN_ID = ?"    //delete row where value in column ID matches with taskId   // ? will be replaced by taskId within whereArgs
        val whereArgs = arrayOf(taskId.toString())   //provide values that will replace the placeholder in the whereClause

        db.delete(TABLE_NAME, whereClause, whereArgs)   //used to delete rows from table    //It will go into table and delete specific taskID from COLUMN_ID & ultimately delete title and description
        db.close()

    }


}