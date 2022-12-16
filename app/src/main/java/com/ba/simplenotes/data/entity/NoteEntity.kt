package com.ba.simplenotes.data.entity

import androidx.room.Entity
import androidx.room.Index
import com.ba.simplenotes.domain.model.Note

@Entity(
	tableName = "notes",
	indices = [Index(value = ["id"])],
	primaryKeys = ["id"],
)
data class NoteEntity(
	val id: Int? = null,
	val dateCreated: Long,
	val dateModified: Long,
	val title: String,
	val contents: String,
) {
	fun toNote() = Note(
		id = id,
		dateCreated = dateCreated,
		dateModified = dateModified,
		title = title,
		contents = contents,
	)

	companion object {
		fun fromNote(note: Note) = NoteEntity(
			id = note.id,
			dateCreated = note.dateCreated,
			dateModified = note.dateModified,
			title = note.title,
			contents = note.contents,
		)
	}
}

