package com.example.cashtrackercompose.repo

import android.content.ContentValues
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import android.widget.Toast
import com.example.cashtrackercompose.DB_NAME
import com.example.cashtrackercompose.entry.Entry
import com.example.cashtrackercompose.model.EntryData
import java.util.Calendar

class EntryDataImpl(
    private val context: Context
) : EntryData {

    private var db: SQLiteDatabase

    init {
        db = context.openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null)
        db.execSQL("CREATE TABLE IF NOT EXISTS entry(Place TEXT, Product TEXT, Notes TEXT, Time TEXT, Cost REAL)")
    }



    override suspend fun getEntries(): List<Entry> {

        val data = mutableListOf<Entry>()

        db = context.openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null)

        val resultSet = db.rawQuery("SELECT * FROM entry", null)
        resultSet.moveToFirst()
        if (resultSet.count == 0) {
            Log.i("Katze", "vor resultset")
        }

        for (i in 1..resultSet.count) {
            data.add(
                Entry(
                    place = resultSet.getString(0),
                    product = resultSet.getString(1),
                    notes = resultSet.getString(2),
                    date = resultSet.getString(3),
                    price = resultSet.getFloat(4)
                )
            )
            resultSet.moveToNext()
            Log.i("Katze", "resultset")
        }

        resultSet.close()

        db.close()
        return data
    }

    override suspend fun addEntry(
        entry: Entry
    ): Boolean {

        db = context
            .openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null)
        db.execSQL("CREATE TABLE IF NOT EXISTS entry(Place TEXT, Product TEXT, Notes TEXT, Time TEXT, Cost REAL)")
        val date = Calendar.getInstance().time
        val dateString: String = date.toString()
        val dateShort = dateString.subSequence(0, 19).toString().lowercase()

        val insert = ContentValues()
        insert.put("Place", entry.place)
        insert.put("Product", entry.product)
        insert.put("Notes", entry.notes)
        insert.put("Time", dateShort)
        insert.put("Cost", entry.price)

        try {
            db.insert("entry", null, insert)
            Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
            db.close()
            return true

        } catch (e: Exception) {
            Log.e("Add entry", e.message?: "Error add entry")
            return false
        }



    }

    override suspend fun deleteEntry(
        entry: Entry
    ): Boolean {
        db = context.openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null)
        val place = entry.place
        val product = entry.product
        val date = entry.date
        val notes = entry.notes
        val price = entry.price
        try {
            db.execSQL("DELETE FROM entry WHERE Place = '$place' AND Product = '$product' AND Notes = '$notes' AND Time = '$date' AND Cost = '$price'")
            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
            db.close()
            return true
        } catch (e:Exception) {
            Log.e("Delete Error", e.message?:"Error delete")
            return false
        }

    }

}