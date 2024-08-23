package com.example.notes

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.notes.databinding.ActivityAddNoteBinding
import com.example.notes.databinding.ActivityMainBinding

class addNote : AppCompatActivity() {

    private lateinit var binding :ActivityAddNoteBinding
    private lateinit var db: NoteDB


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NoteDB(this)

        binding.buttonAdd.setOnClickListener{
            val title =binding.editTextAddtitle.text.toString()
            val content = binding.editTextAdddisc.text.toString()
            val note =Note(0,title,content)

            db.addNote(note)
            finish()
            Toast.makeText(this,"Note Added", Toast.LENGTH_SHORT).show()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}