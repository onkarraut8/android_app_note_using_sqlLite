package com.example.notes

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*

class NoteAdaptor(private var notes:List<Note>,context: Context): Adapter<NoteAdaptor.NoteViewHolder>() {

    private  var db: NoteDB = NoteDB(context)

       class NoteViewHolder(itemView : View): ViewHolder(itemView){
         val titleTextView : TextView = itemView.findViewById(R.id.ViewTitle)
           val contentTextView: TextView = itemView.findViewById(R.id.ViewContent)
           var updateButton :ImageView =itemView.findViewById(R.id.imageEditNote)
           var deleteButton :ImageView= itemView.findViewById(R.id.imageDeleteNote)
       }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteAdaptor.NoteViewHolder {
       val view =LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent,false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteAdaptor.NoteViewHolder, position: Int) {
       val note = notes[position]
        holder.titleTextView.text =note.title
        holder.contentTextView.text =note.content

      holder.updateButton.setOnClickListener{
        val intent = Intent(holder.itemView.context,updateNote::class.java).apply {
            putExtra("note_id",note.id)
        }
        holder.itemView.context.startActivity(intent)
     }
        holder.deleteButton.setOnClickListener{
            db.deleteNote(note.id)
            refreshData(db.getAllNote())
            Toast.makeText(holder.itemView.context,"Note Deleted",Toast.LENGTH_SHORT).show()
        }

    }

    override fun getItemCount(): Int =notes.size


    fun refreshData(newNote :List<Note>){
        notes = newNote
        notifyDataSetChanged()
    }
}
