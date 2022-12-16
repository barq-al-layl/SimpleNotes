package com.ba.simplenotes.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ba.simplenotes.data.entity.NoteEntity

@Database(
	entities = [NoteEntity::class],
	version = 1,
	exportSchema = false,
)
abstract class SimpleNotesDatabase : RoomDatabase() {

	abstract val dao: SimpleNotesDao

	companion object {
		const val DATABASE_NAME = "simple_notes_database"
	}
}