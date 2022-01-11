package com.example.android.taskmanagementapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ViewEntiti : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_entiti)

        val intent = this.getIntent()
        val entiti = intent.getStringExtra("entiti")

        supportActionBar?.setTitle(entiti)

        //read database
        val db = openOrCreateDatabase("projectnative", MODE_PRIVATE, null)
        val sql = "SELECT note from taskapp where entiti='$entiti'"
        val cursor = db.rawQuery(sql, null)

        var note = ""

        while (cursor.moveToNext()){
            note = cursor.getString(0)
        }
        cursor.close()

        var unote = findViewById<EditText>(R.id.innote)

        unote.setText(note)

        val btnDel = findViewById<FloatingActionButton>(R.id.delBtn)
        var delDialog : AlertDialog? = null
        val delBuilder = AlertDialog.Builder(this)
        delBuilder.setTitle("Delete Process")
        delBuilder.setMessage("Are you sure to delete?")

        delBuilder.setNeutralButton("Cancel"){dialogInterface, which ->
            subToast("Delete Cancelled")
        }

        delBuilder.setPositiveButton("Yes"){dialogInterface, which ->
            val db = openOrCreateDatabase("projectnative", MODE_PRIVATE, null)
            val sql = "DELETE FROM taskapp where entiti = '$entiti';"
            db.execSQL(sql)

            subToast("Entiti $entiti deleted!")
            val intent = Intent(this, MainActivity::class.java).apply {
            }
            startActivity(intent)
        }

        delDialog = delBuilder.create()
        btnDel.setOnClickListener(){
            delDialog.show()
        }

        //=======================================================
        val btnEdit = findViewById<FloatingActionButton>(R.id.editBtn)
        var editDialog : AlertDialog? = null
        val editBuilder = AlertDialog.Builder(this)
        editBuilder.setTitle("Update Process")
        editBuilder.setMessage("Are you sure to update the data?")

        editBuilder.setNeutralButton("No"){dialogInterface, which ->
            subToast("Update Cancelled")
        }

        editBuilder.setPositiveButton("Yes"){dialogInterface, which ->

            var unote = findViewById<EditText>(R.id.innote)

            val no = unote.text.toString()

            val db = openOrCreateDatabase("projectnative", MODE_PRIVATE, null)
            val sql = "UPDATE taskapp SET note = '$no' WHERE entiti = '$entiti';"
            db.execSQL(sql)

            subToast("Entiti $entiti updated!")
            val intent = Intent(this, MainActivity::class.java).apply {
            }
            startActivity(intent)
        }

        editDialog = editBuilder.create()
        btnEdit.setOnClickListener(){
            editDialog.show()
        }
    }
    private fun subToast(msg: String){
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()
    }
}