package com.example.android.taskmanagementapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast

class AddEntiti : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_entiti)

        val btnSave = findViewById<ImageButton>(R.id.btnSave)
        btnSave.setOnClickListener(){
            val entiti = findViewById<EditText>(R.id.inEntiti)
            val note = findViewById<EditText>(R.id.innote1)
            //val password = findViewById<EditText>(R.id.inPassword1)

            val emptyLevel = emptiness(entiti,note)
            if(emptyLevel>0){
                //which field is empty
                when(emptyLevel){
                    5 -> subToast("Entiti must no empty!")
                    6 -> subToast("Note must no empty!")
                    7 -> subToast("Password must no empty!")
                    11 -> subToast("Entiti and Note must no empty!")
                    12 -> subToast("Entiti and Password must no empty!")
                    13 -> subToast("Note and Password must no empty!")
                    18 -> subToast("Entiti, Note and Password must no empty!")
                }
            }else{
                //check for exist entiti
                val status = checkKey(entiti.text.toString())
                val ent = entiti.text.toString()
                val uno = note.text.toString()
                //val passwd = password.text.toString()
                if(!status){
                    val db = openOrCreateDatabase("projectnative", MODE_PRIVATE, null)
                    val sql = "INSERT INTO taskapp (entiti,note) VALUES ('$ent','$uno');"
                    db.execSQL(sql)
                    subToast("New entiti $ent added!")
                    val intent = Intent(this, MainActivity::class.java).apply {
                    }
                    startActivity(intent)

                }else{
                    subToast("Entiti already exists inside Database!")
                }
            }
        }
    }

    private fun checkKey(entiti: String):Boolean {
        val db = openOrCreateDatabase("projectnative", MODE_PRIVATE, null)
        val sql = "SELECT * FROM taskapp where entiti='$entiti'"
        val cursor = db.rawQuery(sql, null)
        var out =false
        if(cursor.count>0)
            out=true
        return out
    }

    private fun emptiness(entiti:EditText,note:EditText):Int{
        var empty = 0

        if(entiti.text.isEmpty())
            empty +=5

        if(note.text.isEmpty())
            empty +=6

        return empty

    }
    private fun subToast(msg: String){
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()
    }
}