package com.example.notes

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class NoteDB (context : Context): SQLiteOpenHelper(context, DATABASE_NAME,null, DATABASE_VERSION){

    companion object{
        private const val DATABASE_NAME = "noteapp.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "note"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_CONTENT = "content"
    }

    override fun onCreate(db: SQLiteDatabase?) {
       val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY,$COLUMN_TITLE TEXT,$COLUMN_CONTENT TEXT)"
      db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun addNote(note : Note){
     val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, note.title)
            put(COLUMN_CONTENT, note.content)
        }
        db.insert(TABLE_NAME,null ,values)
        db.close()
    }

    fun updateNote(note : Note){
        val db = writableDatabase
        val values = ContentValues().apply {

            put(COLUMN_TITLE, note.title)
            put(COLUMN_CONTENT, note.content)
        }
        val where ="$COLUMN_ID =?"
        val whereArg = arrayOf(note.id.toString())
        db.update(TABLE_NAME,values,where,whereArg)
        db.close()
    }


    fun getNoteById(noteId:Int):Note{
        val db =readableDatabase
        val query ="SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID =$noteId"

        val cursor =db.rawQuery(query,null)
          cursor.moveToFirst()
        val id= cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val title= cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
        val content= cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))

        cursor.close()
        db.close()
        return Note(id,title,content)
    }

    fun deleteNote(noteId:Int){
     val db = writableDatabase
        val where ="$COLUMN_ID = ?"
        val whereArg = arrayOf(noteId.toString())
        db.delete(TABLE_NAME,where,whereArg)
        db.close()
    }

    fun getAllNote():List<Note>{
        val noteList = mutableListOf<Note>()
        val db =readableDatabase
        val query ="SELECT * FROM $TABLE_NAME"
        val cursor =db.rawQuery(query,null)

        while (cursor.moveToNext()){
            val id=cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title= cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val content=cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))

            val note =Note(id,title,content)
            noteList.add(note)
        }
        cursor.close()
        db.close()
        return noteList
    }

}