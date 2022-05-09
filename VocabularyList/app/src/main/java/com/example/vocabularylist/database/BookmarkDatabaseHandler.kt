package com.example.vocabularylist.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.example.vocabularylist.vocas.VocaModel

class BookmarkDatabaseHandler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object{
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "BookmarkDatabase"
        private const val TABLE_BOOKMARK = "BookmarkTable"

        private const val KEY_ID = "_id"
        private const val KEY_WORD = "word"
        private const val KEY_INT_SOURCE = "int_source"
        private const val KEY_STR_SOURCE = "str_source"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_BOOKMARK_TABLE = ("CREATE TABLE " + TABLE_BOOKMARK + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_WORD + " TEXT,"
                + KEY_INT_SOURCE + " INTEGER,"
                + KEY_STR_SOURCE + " TEXT)")


        db?.execSQL(CREATE_BOOKMARK_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_BOOKMARK")
        onCreate(db)
    }

    fun addVoca(voca : VocaModel) : Long{
        val db = this.writableDatabase

        val contentValues = ContentValues()

        contentValues.put(KEY_WORD, voca.word)
        contentValues.put(KEY_INT_SOURCE, voca.src)
        contentValues.put(KEY_STR_SOURCE, voca.srcPath)

        val result = db.insert(TABLE_BOOKMARK, null, contentValues)
        db.close()

        return result
    }

    fun deleteVoca(voca : VocaModel) : Int {
        val db = this.writableDatabase
        val success = db.delete(TABLE_BOOKMARK, KEY_ID + "=" + voca.id, null)
        db.close()

        return success
    }


    fun getBookmarkVocaList() : ArrayList<VocaModel>{
        val bookmark : ArrayList<VocaModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TABLE_BOOKMARK"
        val db = this.readableDatabase

        try {
            val cursor : Cursor = db.rawQuery(selectQuery, null)
            if(cursor.moveToFirst()){
                do {
                    val voca = VocaModel(
                            cursor.getInt(cursor.getColumnIndex(KEY_ID)),
                            cursor.getString(cursor.getColumnIndex(KEY_WORD)),
                            cursor.getInt(cursor.getColumnIndex(KEY_INT_SOURCE)),
                            cursor.getString(cursor.getColumnIndex(KEY_STR_SOURCE)),
                            true
                    )

                    bookmark.add(voca)
                } while(cursor.moveToNext())
            }
        } catch (e : SQLiteException){
            db.execSQL(selectQuery)
            return ArrayList()
        }
        return bookmark
    }

    fun updateVoca(voca : VocaModel) : Int {
        val db = this.writableDatabase

        val contentValues = ContentValues()

        contentValues.put(KEY_WORD, voca.word)
        contentValues.put(KEY_INT_SOURCE, voca.src)
        contentValues.put(KEY_STR_SOURCE, voca.srcPath)

        val success = db.update(
                TABLE_BOOKMARK,
                contentValues,
                KEY_ID + "=" + voca.id,
                null)

        db.close()

        return success
    }

}