package uz.ilmiddin1701.note.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import uz.ilmiddin1701.note.models.NoteData

class MyDbHelper(var context: Context) : SQLiteOpenHelper(context, DB_NAME, null, VERSION), MyDbInterface {

    companion object {
        const val DB_NAME = "notes"
        const val TABLE_NAME = "notes_table"
        const val VERSION = 1

        const val ID = "id"
        const val NAME = "name"
        const val TEXT = "text"
        const val DATE = "date"
        const val TIME = "time"
        const val IMAGES = "images"
        const val VOICES = "voices"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val query = "create table $TABLE_NAME (" +
                "$ID integer not null primary key autoincrement unique, " +
                "$NAME text not null, $TEXT text not null, " +
                "$DATE text not null, $TIME text not null, " +
                "$IMAGES text not null, $VOICES text not null)"
        p0?.execSQL(query)
    }
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {}

    override fun addNote(noteData: NoteData) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(NAME, noteData.name)
        contentValues.put(TEXT, noteData.text)
        contentValues.put(DATE, noteData.date)
        contentValues.put(TIME, noteData.time)
        contentValues.put(IMAGES, noteData.images)
        contentValues.put(VOICES, noteData.voices)
        database.insert(TABLE_NAME, null, contentValues)
        database.close()
    }

    override fun editNote() {

    }

    @SuppressLint("Recycle")
    override fun showNotes(): List<NoteData> {
        val list = ArrayList<NoteData>()
        val database = this.readableDatabase
        val query = "select * from $TABLE_NAME"
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val noteData = NoteData(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6)
                )
                list.add(noteData)
            }while (cursor.moveToNext())
        }
        return list
    }

    override fun deleteNote() {

    }
}