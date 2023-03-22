package com.example.speedrun6.service

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

data class TicketData(
    val id: Int,
    val name: String,
    val email: String,
    val phone: String,
    val type: String,
    val exhibit: String,
    val date: String,
)

class SqliteHelper(context: Context): SQLiteOpenHelper(context, "ticketDb", null, 1) {
    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL(
            """
                CREATE TABLE tickets (
                    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    name TEXT NOT NULL,
                    email TEXT NOT NULL,
                    phone TEXT NOT NULL,
                    type TEXT NOT NULL,
                    exhibit TEXT NOT NULL,
                    date TEXT NOT NULL
                );
            """.trimIndent()
        )
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL(
            """
                DROP TABLE IF EXISTS tickets;
            """.trimIndent()
        )
    }

    fun addTicket(
        name: String,
        email: String,
        phone: String,
        type: String,
        exhibit: String,
        date: String,
    ) {
        writableDatabase.execSQL(
            """
                INSERT INTO tickets (
                    name, email, phone, type, exhibit, date
                ) VALUES (
                    '$name', '$email', '$phone', '$type', '$exhibit', '$date'
                );
            """.trimIndent()
        )
    }

    fun getAllTickets(): ArrayList<TicketData> {
        val result = arrayListOf<TicketData>()
        val cursor = readableDatabase.rawQuery(
            """
                SELECT * FROM tickets;
            """.trimIndent(),
            null
        )
        while (cursor.moveToNext()) {
            result.add(
                TicketData(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                )
            )
        }
        cursor.close()
        return result
    }
}