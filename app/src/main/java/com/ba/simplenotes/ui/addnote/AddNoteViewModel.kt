package com.ba.simplenotes.ui.addnote

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ba.simplenotes.domain.model.Note
import com.ba.simplenotes.domain.repository.SimpleNotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(
	private val repository: SimpleNotesRepository,
	savedStateHandle: SavedStateHandle,
) : ViewModel() {
	private val _noteTitle = MutableStateFlow("")
	val noteTitle = _noteTitle.asStateFlow()

	private val _noteContent = MutableStateFlow("")
	val noteContent = _noteContent.asStateFlow()

	private var note = Note(
		id = null,
		dateCreated = 0,
		dateModified = 0,
		title = "",
		contents = "",
	)

	init {
		val noteId: Int? = savedStateHandle["noteId"]
		noteId?.let { id ->
			if (id == -1) return@let
			viewModelScope.launch {
				note = repository.getNoteById(id) ?: return@launch
				_noteTitle.update { note.title }
				_noteContent.update { note.contents }
			}
		}
	}

	fun saveNote() {
		if (noteTitle.value.isEmpty() && noteContent.value.isEmpty()) return
		val timeStamp = System.currentTimeMillis()
		note = note.copy(
			dateCreated = if (note.id == null) timeStamp else note.dateCreated,
			dateModified = timeStamp,
			title = noteTitle.value,
			contents = noteContent.value,
		)
		viewModelScope.launch {
			repository.saveNote(note)
		}
	}

	fun onNoteTitleChange(value: String) {
		_noteTitle.update { value }
	}

	fun onNoteContentChange(value: String) {
		_noteContent.update { value }
	}
}