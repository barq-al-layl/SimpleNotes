package com.ba.simplenotes.data.database

import androidx.room.*
import com.ba.simplenotes.data.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SimpleNotesDao {

	@Query("SELECT * FROM notes ORDER BY dateCreated")
	fun getNotes(): Flow<List<NoteEntity>>

	@Query("SELECT * FROM notes WHERE id = :id")
	suspend fun getNoteBy(id: Int): NoteEntity?

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertNote(note: NoteEntity)

	@Delete
	suspend fun deleteNote(note: NoteEntity)
}