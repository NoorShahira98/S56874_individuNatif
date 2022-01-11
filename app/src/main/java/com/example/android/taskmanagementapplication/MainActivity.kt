package com.example.android.taskmanagementapplication

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.File

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var entities = ArrayList<String>()

        if(!dbExists(this, "projectnative")){
            createDB();

        }
        val db:SQLiteDatabase = openOrCreateDatabase("projectnative", MODE_PRIVATE, null)
        val sql = "SELECT entiti from taskapp"
        var c: Cursor = db.rawQuery(sql,null)
        while (c.moveToNext()){
            val entiti = c.getString(0)
            entities.add(entiti)
        }
        c.close()

        val myAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, entities)
        val lv = findViewById<ListView>(R.id.lv)
        lv.setAdapter(myAdapter)
        lv.onItemClickListener = AdapterView.OnItemClickListener{ adapter, v, position, arg3 ->
            val value = adapter.getItemAtPosition(position).toString()
            val intent = Intent(this, ViewEntiti::class.java).apply {
                putExtra("entiti", value.toString())
            }
            startActivity(intent)
        }

        val fab = findViewById<FloatingActionButton>(R.id.fab1)
        fab.setOnClickListener(){
            val intent = Intent(this, AddEntiti::class.java).apply {
            }
            startActivity(intent)
        }
    }

    private fun dbExists(c: Context, dbName: String):Boolean{
        val dbFile: File = c.getDatabasePath(dbName)
        return dbFile.exists()
    }

    private fun createDB(){
        val db = openOrCreateDatabase("projectnative", MODE_PRIVATE, null)
        subToast("Database projectnative created")
        val sqlText = "CREATE TABLE IF NOT EXISTS taskapp" +
                "(entiti VARCHAR(30) PRIMARY KEY, " +
                "note VARCHAR(30) NOT NULL" +
                //"password VARCHAR(30) NOT NULL " +
                ");"
        subToast("Table taskapp created")
        db.execSQL(sqlText)
        var nextSQL = "INSERT INTO taskapp(entiti, note) VALUES ('Cyber', '2/2/22', 'project');"
        db.execSQL(nextSQL)
        nextSQL = "INSERT INTO taskapp(entiti, note) VALUES ('Cyber', '2/2/22', 'project');"
        db.execSQL(nextSQL)
        nextSQL = "INSERT INTO taskapp(entiti, note) VALUES ('Cyber', '2/2/22', 'project');"
        db.execSQL(nextSQL)
        subToast("3 sample entities added!")
    }
    private fun subToast(msg: String){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }
}