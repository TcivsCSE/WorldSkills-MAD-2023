package com.example.basicbuild.service

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteOpenHelper

data class TicketData (
    val id: Int,
    val exhibitName: String,
    val ticketType: String,
    val price: Int,
    val name: String,
    val email: String,
    val phone: String
)

class SqliteHelper(context: Context, factory: CursorFactory?): SQLiteOpenHelper(context, "TicketDb", factory, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(
            """
                CREATE TABLE tickets(
                    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
                    exhibitName TEXT NOT NULL,
                    ticketType TEXT NOT NULL,
                    price INTEGER NOT NULL,
                    name TEXT NOT NULL,
                    email TEXT NOT NULL,
                    phone TEXT NOT NULL
                )
            """.trimIndent()
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL(
            """
                DROP TABLE IF EXISTS tickets
            """.trimIndent()
        )
    }

    fun addTicket(exhibitName: String, ticketType: String, price: Int, name: String, email: String, phone: String) {
        writableDatabase.execSQL(
            """
                INSERT INTO tickets(
                    exhibitName, ticketType, price, name, email, phone
                ) VALUES (
                    '$exhibitName', '$ticketType', $price, '$name', '$email', '$phone'
                )
            """.trimIndent()
        )
    }

    fun getTickets(): ArrayList<TicketData> {
        val cursor = readableDatabase.rawQuery(
            """
                SELECT * FROM tickets
            """.trimIndent(),
            null
        )
        val result = arrayListOf<TicketData>()
        while (cursor.moveToNext()) {
            result.add(
                TicketData(
                    id = cursor.getInt(0),
                    exhibitName = cursor.getString(1),
                    ticketType = cursor.getString(2),
                    price = cursor.getInt(3),
                    name = cursor.getString(4),
                    email = cursor.getString(5),
                    phone = cursor.getString(6)
                )
            )
        }
        cursor.close()
        return result
    }
}