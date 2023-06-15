package dev.nightfeather.basic_build.service

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

data class TicketData (
    val id: String,
    val name: String,
    val price: Int,
    val purchased_at: Int,
    val expired_at: String,
    val type: String,
)

class SqliteHelper(context: Context): SQLiteOpenHelper(context, "ticketDb", null, 1) {
    override fun onCreate(p0: SQLiteDatabase?) {
        p0!!.execSQL(
            """
                CREATE TABLE tickets (
                    id TEXT PRIMARY KEY NOT NULL,
                    name TEXT NOT NULL,
                    price INTEGER NOT NULL,
                    purchased_at INTEGER NOT NULL,
                    expired_at TEXT NOT NULL,
                    type TEXT NOT NULL
                );
            """.trimIndent()
        )
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0!!.execSQL(
            """
                DROP TABLE IF EXISTS tickets;
            """.trimIndent()
        )
    }

    fun addTicket(data: TicketData) {
        writableDatabase.execSQL(
            """
                INSERT INTO tickets (
                    id, name, price, purchased_at, expired_at, type
                ) VALUES (
                    '${data.id}', '${data.name}', '${data.price}', '${data.purchased_at}', '${data.expired_at}', '${data.type}', 
                );
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
        val data = arrayListOf<TicketData>()
        while (cursor.moveToNext()) {
            data.add(
                TicketData(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getInt(3),
                    cursor.getString(4),
                    cursor.getString(5),
                )
            )
        }
        cursor.close()
        return data
    }
}