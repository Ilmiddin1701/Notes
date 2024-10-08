package uz.ilmiddin1701.note.adapters

import android.graphics.Color
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import uz.ilmiddin1701.note.databinding.ItemNoteBinding
import uz.ilmiddin1701.note.models.NoteData

class NotesAdapter(var notesActionListener: NotesActionListener, var list: ArrayList<NoteData>) : Adapter<NotesAdapter.Vh>() {

    inner class Vh(var itemNoteBinding: ItemNoteBinding) : ViewHolder(itemNoteBinding.root) {
        fun onBind(noteData: NoteData, position: Int) {
            itemNoteBinding.apply {
                noteName.text = noteData.name
                noteName.isSelected = true
                noteTexts.text = Html.fromHtml(noteData.text, Html.FROM_HTML_MODE_COMPACT)
                noteTime.text = noteData.time
                root.setOnClickListener {
                    notesActionListener.onNoteClick(noteData, position)
                }
                if (position == 0 || position == 1) {
                    visibilityView1.visibility = View.VISIBLE
                } else {
                    visibilityView1.visibility = View.GONE
                }
                if (position == list.size - 1 || position == list.size - 1) {
                    visibilityView2.visibility = View.VISIBLE
                } else {
                    visibilityView2.visibility = View.GONE
                }
                noteData.apply {
                    if (nameTextColor != "") {
                        noteName.setTextColor(Color.parseColor(noteData.nameTextColor))
                    }
                    if (nameBackgroundColor != "") {
                        noteName.setBackgroundColor(Color.parseColor(noteData.nameBackgroundColor))
                    }
                    if (noteTextColor != "") {
                        noteName.setTextColor(Color.parseColor(noteData.noteTextColor))
                    }
                    if (noteBackgroundColor != "") {
                        noteName.setBackgroundColor(Color.parseColor(noteData.noteBackgroundColor))
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    interface NotesActionListener {
        fun onNoteClick(noteData: NoteData, position: Int)
    }
}