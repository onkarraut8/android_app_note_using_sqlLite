package com.example.notes

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.notes.databinding.ActivityAddNoteBinding
import com.example.notes.databinding.ActivityUpdateNoteBinding

class updateNote : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateNoteBinding
    private lateinit var db :NoteDB
    private  var noteId:Int =-1
    private lateinit var note :Note


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUpdateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NoteDB(this)

       noteId = intent.getIntExtra("note_id",-1)
       if(noteId == -1){
           finish()
           return
       }

        val note =db.getNoteById(noteId)
        binding.editTextUtitle.setText(note.title)
        binding.editTextIdisc.setText(note.content)

        binding.buttonUpdate.setOnClickListener{
            val newtitle= binding.editTextUtitle.text.toString()
            val newcontent= binding.editTextIdisc.text.toString()
            val newNote =Note(noteId,newtitle,newcontent)
            db.updateNote(newNote)

            finish()
            Toast.makeText(this,"Note Updates",Toast.LENGTH_SHORT).show()
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}