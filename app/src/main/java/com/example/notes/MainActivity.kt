package com.example.notes

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding :ActivityMainBinding

private lateinit var db :NoteDB
private lateinit var noteAdaptor: NoteAdaptor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding =ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = NoteDB(this)
        noteAdaptor = NoteAdaptor(db.getAllNote(),this)

       binding.noteView.layoutManager = LinearLayoutManager(this)
       binding.noteView.adapter =noteAdaptor



        binding.floatingActionAddBtn.setOnClickListener{
           val intent = Intent(this, addNote::class.java)
           startActivity(intent)
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onResume() {
        super.onResume()
        noteAdaptor.refreshData(db.getAllNote())
    }
}