package com.ba.simplenotes.di

import android.app.Application
import androidx.room.Room
import com.ba.simplenotes.data.database.SimpleNotesDatabase
import com.ba.simplenotes.data.repository.SimpleNotesRepositoryImpl
import com.ba.simplenotes.domain.repository.SimpleNotesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

	@Provides
	@Singleton
	fun providesSimpleNotesDatabase(application: Application): SimpleNotesDatabase {
		return Room.databaseBuilder(
			application,
			SimpleNotesDatabase::class.java,
			SimpleNotesDatabase.DATABASE_NAME,
		).build()
	}

	@Provides
	@Singleton
	fun providesSimpleNotesRepository(database: SimpleNotesDatabase): SimpleNotesRepository {
		return SimpleNotesRepositoryImpl(database.dao)
	}
}