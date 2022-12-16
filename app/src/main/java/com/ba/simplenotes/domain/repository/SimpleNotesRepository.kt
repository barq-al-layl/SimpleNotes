package com.ba.simplenotes.domain.repository

import com.ba.simplenotes.domain.model.Note
import com.ba.simplenotes.domain.model.Order
import kotlinx.coroutines.flow.Flow

interface SimpleNotesRepository {
	fun getNotes(order: Order): Flow<List<Note>>
	suspend fun getNoteById(id: Int): Note?
	suspend fun saveNote(note: Note)
	suspend fun deleteNote(note: Note)
}