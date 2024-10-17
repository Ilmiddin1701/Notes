package uz.ilmiddin1701.note.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import uz.ilmiddin1701.note.R
import uz.ilmiddin1701.note.adapters.NotesAdapter
import uz.ilmiddin1701.note.adapters.SowImagesAdapter
import uz.ilmiddin1701.note.databinding.FragmentHomeBinding
import uz.ilmiddin1701.note.db.MyDbHelper
import uz.ilmiddin1701.note.models.NoteData
import uz.ilmiddin1701.note.utils.MySharedPreferences

class HomeFragment : Fragment(), NotesAdapter.NotesActionListener {
    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    private lateinit var notesAdapter: NotesAdapter
    private lateinit var myDbHelper: MyDbHelper
    private lateinit var notesList: ArrayList<NoteData>
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        myDbHelper = MyDbHelper(requireContext())
        binding.apply {
            // Background Color
            if (MySharedPreferences.backgroundColor != "empty") {
                binding.root.setBackgroundColor(Color.parseColor(MySharedPreferences.backgroundColor))
            } else binding.root.setBackgroundColor(Color.parseColor("#F0F8FF"))


            val drawable = edtSearch.compoundDrawablesRelative[2] // `drawableEnd` ni olish
            when (MySharedPreferences.bottomNavBarColor) {
                0 -> {
                    drawable?.setTint(Color.parseColor("#096EB4"))
                    searchCard.strokeColor = Color.parseColor("#096EB4")
                }
                1 -> {
                    drawable?.setTint(Color.parseColor("#096EB4"))
                    searchCard.strokeColor = Color.parseColor("#096EB4")
                }
                2 -> {
                    drawable?.setTint(Color.parseColor("#673AB7"))
                    searchCard.strokeColor = Color.parseColor("#673AB7")
                }
                3 -> {
                    drawable?.setTint(Color.parseColor("#FF5722"))
                    searchCard.strokeColor = Color.parseColor("#FF5722")
                }
                4 -> {
                    drawable?.setTint(Color.parseColor("#FF9800"))
                    searchCard.strokeColor = Color.parseColor("#FF9800")
                }
                5 -> {
                    drawable?.setTint(Color.parseColor("#E91E63"))
                    searchCard.strokeColor = Color.parseColor("#E91E63")
                }
                6 -> {
                    drawable?.setTint(Color.parseColor("#009688"))
                    searchCard.strokeColor = Color.parseColor("#009688")
                }
                7 -> {
                    drawable?.setTint(Color.parseColor("#4CAF50"))
                    searchCard.strokeColor = Color.parseColor("#4CAF50")
                }
                8 -> {
                    drawable?.setTint(Color.parseColor("#363F5E"))
                    searchCard.strokeColor = Color.parseColor("#363F5E")
                }
                9 -> {
                    drawable?.setTint(Color.parseColor("#457B9D"))
                    searchCard.strokeColor = Color.parseColor("#457B9D")
                }
                10 -> {
                    drawable?.setTint(Color.parseColor("#B93E20"))
                    searchCard.strokeColor = Color.parseColor("#B93E20")
                }
            }

            notesList = ArrayList()
            notesList.addAll(myDbHelper.showNotes())
            notesAdapter = NotesAdapter(this@HomeFragment, notesList)
            rvNotes.adapter = notesAdapter

            // Search Note
            edtSearch.addTextChangedListener {
                notesList.clear()
                myDbHelper.showNotes().forEach { note ->
                    if (note.name!!.lowercase().contains(it.toString().lowercase())) {
                        (notesList).add(note)
                    }
                }
                notesAdapter.notifyDataSetChanged()
            }
        }
        return binding.root
    }

    override fun onNoteClick(noteData: NoteData) {
        findNavController().navigate(R.id.showFragment, bundleOf("keyNoteData" to noteData))
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onNoteLongClick(noteData: NoteData, position: Int) {
        val dialog = AlertDialog.Builder(context).create()
        dialog.setTitle("Delete note?")
        dialog.setMessage("Deleting this note will result in the deletion of all data collected on it!")
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Delete") { _, _ ->
            myDbHelper.deleteNote(noteData)
            notesList.clear()
            notesList.addAll(myDbHelper.showNotes())
            notesAdapter.notifyDataSetChanged()
        }
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel") { _, _ -> dialog.cancel() }
        dialog.show()
    }
}