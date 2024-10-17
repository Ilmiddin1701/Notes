package uz.ilmiddin1701.note.db

import uz.ilmiddin1701.note.models.NoteData

interface MyDbInterface {
    fun addNote(noteData: NoteData)
    fun showNotes() : List<NoteData>
    fun editNote(noteData: NoteData)
    fun deleteNote(noteData: NoteData)
}