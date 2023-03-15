package com.example.speedrun1.service

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

data class TicketData(
    val id: Int,
    val name: String,
    val email: String,
    val phone: String,
    val exhibitName: String,
    val ticketType: String
)

class SqliteHelper(context: Context): SQLiteOpenHelper(context, "ticketDb", null, 1) {
    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL(
            """
                CREATE TABLE ticket(
                    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    name TEXT NOT NULL,
                    email TEXT NOT NULL,
                    phone TEXT NOT NULL,
                    exhibitName TEXT NOT NULL,
                    ticketType TEXT NOT NULL
                )
            """.trimIndent()
        )
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL(
            """
                DROP TABLE IF EXISTS ticket
            """.trimIndent()
        )
    }

    fun addTicket(name: String, email: String, phone: String, exhibitName: String, ticketType: String) {
        writableDatabase.execSQL(
            """
                INSERT INTO ticket (
                    name, email, phone, exhibitName, ticketType
                ) VALUES (
                    '$name', '$email', '$phone', '$exhibitName', '$ticketType'
                )
            """.trimIndent()
        )
    }

    fun getAllTickets(): ArrayList<TicketData> {
        val result = arrayListOf<TicketData>()
        val cursor = readableDatabase.rawQuery(
            """
                SELECT * FROM ticket
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
                )
            )
        }
        cursor.close()
        return result
    }
}