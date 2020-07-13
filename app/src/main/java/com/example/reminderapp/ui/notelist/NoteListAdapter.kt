package com.example.reminderapp.ui.notelist

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.reminderapp.database.Notes
import com.example.reminderapp.databinding.NoteListItemBinding


class NoteListAdapter ( private val noteClickListener: NoteClickListener):RecyclerView.Adapter<NoteListAdapter.ViewHolder>(){

 private var items: MutableList<Notes> = mutableListOf<Notes>()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(NoteListItemBinding.inflate(
            LayoutInflater.from(parent.context),parent,false
        ))

    }

    override fun getItemCount(): Int =items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val note:Notes=items[position]
        holder.apply {
            bind(createOnClickListner(note.id),note)
            itemView.tag=note
        }

        holder.itemView.setOnClickListener {
            noteClickListener.onNoteClickListener(note)
        }
    }

    private fun createOnClickListner(id:Int):View.OnClickListener{
        return View.OnClickListener {
            val direction=NoteListFragmentDirections.actionNoteListFragmentToNoteDetailsFragment()
            it.findNavController().navigate(direction)
        }
    }

    fun setItem(items:List<Notes>){
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }


    inner class ViewHolder(
        private  val binding: NoteListItemBinding
    ):RecyclerView.ViewHolder(binding.root){

        fun bind(listner:View.OnClickListener,item:Notes){
            binding.apply {
                clickListner=listner
                note=item
                checkbox.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                executePendingBindings()

            }
        }

    }

    interface NoteClickListener {
        fun onNoteClickListener(data:Notes)
    }
}
