package uz.ilmiddin1701.note.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import uz.ilmiddin1701.note.R
import uz.ilmiddin1701.note.adapters.NotesAdapter
import uz.ilmiddin1701.note.databinding.FragmentHomeBinding
import uz.ilmiddin1701.note.db.MyDbHelper
import uz.ilmiddin1701.note.models.NoteData
import uz.ilmiddin1701.note.utils.MyData
import uz.ilmiddin1701.note.utils.MySharedPreferences

class HomeFragment : Fragment(), NotesAdapter.NotesActionListener {
    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    private lateinit var notesAdapter: NotesAdapter
    private lateinit var myDbHelper: MyDbHelper
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
            drawable?.setTint(ContextCompat.getColor(requireContext(), R.color.yellow)) // yangi rangni o'rnatish

            val notesList = ArrayList<NoteData>()
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

    override fun onNoteClick(noteData: NoteData, position: Int) {
        findNavController().navigate(R.id.showFragment)
        MyData.noteClick.postValue(true)
    }
}