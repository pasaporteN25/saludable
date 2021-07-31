package com.feluts.saludable.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.feluts.saludable.Comidas
import com.feluts.saludable.User
import java.lang.Exception

class DBSGestor (context: Context, factory:SQLiteDatabase.CursorFactory?): SQLiteOpenHelper(context, DB_NOMBRE, factory, DB_VERS){

    companion object{
        private val DB_NOMBRE = "saludable.db"
        private val DB_VERS = 1
        private val TABLA_USER = "usuarios"
        private val TABLA_COMIDAS = "comidas"

        private val COLUMN_USER = "usuario"
        private val COLUMN_PASS = "contraseña"
        private val COLUMN_MAIL = "mail"
        //De comidas:
        private val COLUMN_ID = "id"
        private val COLUMN_COM = "comida"
        private val COLUMN_PRI = "principal"
        private val COLUMN_SEC = "secundario"
        private val COLUMN_BEB = "bebida"
        private val COLUMN_POS = "postre"
        private val COLUMN_TENT = "tentacion"
        private val COLUMN_HAMB = "hambre"
        private val COLUMN_TS = "timestamp"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val crearTCom = (
                "CREATE TABLE "+ TABLA_COMIDAS + " ( "+ COLUMN_ID + " INTEGER PRIMARY KEY, " +
                        COLUMN_COM + " TEXT NOT NULL, "+
                        COLUMN_PRI + " TEXT NOT NULL, "+
                        COLUMN_SEC + "TEXT NOT NULL, "+
                        COLUMN_BEB + "TEXT NOT NULL, "+
                        COLUMN_POS + "TEXT, "+
                        COLUMN_TENT + "TEXT, "+
                        COLUMN_HAMB + "TEXT," +
                        COLUMN_TS + "TEXT NOT NULL )"
                )

        val crearTUser = (
                "CREATE TABLE "+ TABLA_USER + " ( "+ COLUMN_USER + " TEXT PRIMARY KEY, " +
                        COLUMN_PASS + "TEXT NOT NULL, "+
                        COLUMN_MAIL + "TEXT NOT NULL )"
                )

        db?.execSQL(crearTCom)
        db?.execSQL(crearTUser)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldV: Int, newV: Int) {
        TODO("Not yet implemented")
    }

    fun guardarComida(comida: Comidas): Boolean{
        try{
            val db = this.writableDatabase
            val values = ContentValues()
            values.put(COLUMN_COM, comida.comida)
            values.put(COLUMN_PRI, comida.principal)
            values.put(COLUMN_SEC, comida.secundaria)
            values.put(COLUMN_BEB, comida.bebida)
            values.put(COLUMN_POS, comida.postre)
            values.put(COLUMN_TENT, comida.tentacion)
            values.put(COLUMN_HAMB, comida.hambre)
            values.put(COLUMN_TS, comida.fechayhora)
            db.insert(TABLA_COMIDAS, null, values)
            return true
        } catch (e: Exception){
            Log.e("Error insertar comida",e.message.toString())
            return false
        }
    }

    fun userSaved(user: User):Boolean{
        try{
            val db = this.writableDatabase
            val values = ContentValues()
            values.put(COLUMN_USER, user.user)
            values.put(COLUMN_PASS, user.pass)
            values.put(COLUMN_MAIL, user.mail)
            db.insert(TABLA_USER,null,values)
            return true
        }catch (e: Exception){
            Log.e("Error insertar user",e.message.toString())
            return false
        }

    }

    fun getUser():ArrayList<User>{
        val db = this.readableDatabase
        val Usuario: ArrayList<User> = ArrayList<User>()
        val query = "SELECT * FROM $TABLA_USER"
        val cursor = db.rawQuery(query,null)

        if(cursor.moveToFirst()){
            do{
                val user = cursor.getString(cursor.getColumnIndex(COLUMN_USER))
                val pass = cursor.getString(cursor.getColumnIndex(COLUMN_PASS))
                val mail = cursor.getString(cursor.getColumnIndex(COLUMN_MAIL))
            }while (cursor.moveToNext())
        }
        cursor.close()
        return Usuario
    }
}