package com.ba.simplenotes.data.repository

import com.ba.simplenotes.data.database.SimpleNotesDao
import com.ba.simplenotes.data.entity.NoteEntity
import com.ba.simplenotes.domain.model.Note
import com.ba.simplenotes.domain.model.Order
import com.ba.simplenotes.domain.model.OrderType
import com.ba.simplenotes.domain.repository.SimpleNotesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SimpleNotesRepositoryImpl(private val dao: SimpleNotesDao) : SimpleNotesRepository {
	override fun getNotes(order: Order): Flow<List<Note>> {
		return dao.getNotes().map { notes ->
			val sortedNotes = when (order.orderType) {
				OrderType.Ascending -> when (order) {
					is Order.DateCreated -> notes.sortedBy { it.dateCreated }
					is Order.DateModified -> notes.sortedBy { it.dateModified }
					is Order.Title -> notes.sortedBy { it.title.lowercase() }
				}
				OrderType.Descending -> when (order) {
					is Order.DateCreated -> notes.sortedByDescending { it.dateCreated }
					is Order.DateModified -> notes.sortedByDescending { it.dateModified }
					is Order.Title -> notes.sortedByDescending { it.title.lowercase() }
				}
			}
			sortedNotes.map { it.toNote() }
		}
	}

	override suspend fun getNoteById(id: Int): Note? {
		return dao.getNoteBy(id = id)?.toNote()
	}

	override suspend fun saveNote(note: Note) {
		dao.insertNote(NoteEntity.fromNote(note))
	}

	override suspend fun deleteNote(note: Note) {
		dao.deleteNote(NoteEntity.fromNote(note))
	}
}