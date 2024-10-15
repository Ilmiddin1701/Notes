package uz.ilmiddin1701.note.models

import java.io.Serializable

class NoteData: Serializable {
    var id: Int? = null
    var name: String? = null
    var text: String? = null
    var date: String? = null
    var time: String? = null
    var images: String? = null

    constructor(
        id: Int?,
        name: String?,
        text: String?,
        date: String?,
        time: String?,
        images: String?
    ) {
        this.id = id
        this.name = name
        this.text = text
        this.date = date
        this.time = time
        this.images = images
    }

    constructor(
        name: String?,
        text: String?,
        date: String?,
        time: String?,
        images: String?
    ) {
        this.name = name
        this.text = text
        this.date = date
        this.time = time
        this.images = images
    }
}
