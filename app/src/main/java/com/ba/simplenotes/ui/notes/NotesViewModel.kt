package com.ba.simplenotes.ui.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ba.simplenotes.domain.model.Note
import com.ba.simplenotes.domain.model.Order
import com.ba.simplenotes.domain.model.OrderType
import com.ba.simplenotes.domain.repository.SimpleNotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
	private val repository: SimpleNotesRepository,
) : ViewModel() {

	private val _notes = MutableStateFlow(emptyList<Note>())
	val notes = _notes.asStateFlow()

	private val _notesOrder = MutableStateFlow<Order>(Order.DateCreated(OrderType.Descending))
	val notesOrder = _notesOrder.asStateFlow()

	private val _expandedNoteId = MutableStateFlow<Int?>(null)
	val expandedNoteId = _expandedNoteId.asStateFlow()

	private val _messages = MutableSharedFlow<String>()
	val messages = _messages.asSharedFlow()

	private var lastDeletedNote: Note? = null

	private var getNotesJob: Job? = null

	init {
		getNotes()
	}

	private fun getNotes() {
		getNotesJob?.cancel()
		getNotesJob = repository.getNotes(notesOrder.value).onEach { notes ->
			_notes.update { notes }
		}.launchIn(viewModelScope)
	}

	fun notesOrderTypeChange() {
		_notesOrder.update {
			val orderType = when (it.orderType) {
				OrderType.Ascending -> OrderType.Descending
				OrderType.Descending -> OrderType.Ascending
			}
			it.copy(orderType)
		}
		getNotes()
	}

	fun notesOrderChange(order: Order) {
		_notesOrder.update { order }
		getNotes()
	}

	fun expandNote(noteId: Int?) {
		_expandedNoteId.update {
			noteId.takeIf { id -> id != it }
		}
	}

	fun deleteNote(note: Note) {
		lastDeletedNote = note
		viewModelScope.launch {
			repository.deleteNote(note)
			_messages.emit("Note has been deleted!")
		}
	}

	fun restoreNote() {
		viewModelScope.launch {
			repository.saveNote(lastDeletedNote ?: return@launch)
			lastDeletedNote = null
		}
	}
}