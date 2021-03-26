package project.llac.climapapplication.helper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import project.llac.climapapplication.model.Client

class HelperDB(
    context: Context?
) : SQLiteOpenHelper(context, DATABASE_NAME, null, VERSION) {

    companion object {
        private val DATABASE_NAME = "cliente.db"
        private val VERSION = 1
    }

    val TABLE_NAME = "client"
    val COLUMN_ID = "id"
    val COLUMN_NAME = "name"
    val COLUMN_PHONE = "phone"
    val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
    val CREATE_TABLE = "CREATE TABLE $TABLE_NAME (" +
            "$COLUMN_ID INTEGER NOT NULL, " +
            "$COLUMN_NAME TEXT NOT NULL, " +
            "$COLUMN_PHONE TEXT NOT NULL, " +
            "PRIMARY KEY($COLUMN_ID AUTOINCREMENT) )"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion != newVersion){
            db?.execSQL(DROP_TABLE)
        }
        onCreate(db)
    }

    fun save(client: Client){
        val db = writableDatabase ?: return
        var query = "INSERT INTO $TABLE_NAME ($COLUMN_NAME, $COLUMN_PHONE) VALUES (?,?) "
        var clientData = arrayOf(client.name, client.phone)
        db.execSQL(query, clientData)
        db.close()
    }

    fun search(search: String, isSearchById: Boolean = false): List<Client> {
        val db = readableDatabase ?: return mutableListOf()
        var list = mutableListOf<Client>()
        var where: String?
        var args: Array<String>
        if (isSearchById){
            where = "$COLUMN_ID = ?"
            args = arrayOf("$search")
        } else {
            where = "$COLUMN_NAME LIKE ?"
            args = arrayOf("%$search%")
        }
        var cursor = db.query(TABLE_NAME, null, where, args, null, null, null)

        if (cursor == null){
            db.close()
            return mutableListOf()
        }
        while (cursor.moveToNext()){
            var client = Client(
                    cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_PHONE))
            )
            list.add(client)
        }
        db.close()
        return list
    }

    fun remove(id: Int){
        val db = writableDatabase ?: return
        val where = "id = ?"
        val args = arrayOf("$id")
        db?.delete(TABLE_NAME, where, args)
        db.close()
    }

    fun update(client: Client){
        val db = writableDatabase ?: return
        val clientData = ContentValues()
        clientData.put(COLUMN_NAME, client.name)
        clientData.put(COLUMN_PHONE, client.phone)
        val where = "id = ?"
        val args = arrayOf("${client.id}")
        db.update(TABLE_NAME, clientData, where, args)
        db.close()
    }
}