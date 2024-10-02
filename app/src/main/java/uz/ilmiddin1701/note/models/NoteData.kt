package uz.ilmiddin1701.note.models

class NoteData {
    var name: String? = null
    var text: String? = null
    var date: String? = null
    var time: String? = null
    var nameTextColor: String? = null
    var nameBackgroundColor: String? = null
    var noteTextColor: String? = null
    var noteBackgroundColor: String? = null

    constructor(
        name: String?,
        text: String?,
        date: String?,
        time: String?,
        nameTextColor: String?,
        nameBackgroundColor: String?,
        noteTextColor: String?,
        noteBackgroundColor: String?
    ) {
        this.name = name
        this.text = text
        this.date = date
        this.time = time
        this.nameTextColor = nameTextColor
        this.nameBackgroundColor = nameBackgroundColor
        this.noteTextColor = noteTextColor
        this.noteBackgroundColor = noteBackgroundColor
    }

    constructor(name: String?, text: String?, date: String?, time: String?) {
        this.name = name
        this.text = text
        this.date = date
        this.time = time
    }
}
