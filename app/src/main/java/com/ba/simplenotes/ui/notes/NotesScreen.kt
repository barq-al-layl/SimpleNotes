package com.ba.simplenotes.ui.notes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ba.simplenotes.domain.model.Order
import com.ba.simplenotes.domain.model.OrderType
import com.ba.simplenotes.ui.components.NoteCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(
	navController: NavController,
	viewModel: NotesViewModel = hiltViewModel(),
) {
	val notes by viewModel.notes.collectAsState()
	val notesOrder by viewModel.notesOrder.collectAsState()
	val expandedNoteId by viewModel.expandedNoteId.collectAsState()

	val snackbarHostState = remember { SnackbarHostState() }

	LaunchedEffect(Unit) {
		viewModel.messages.collect { message ->
			val result = snackbarHostState.showSnackbar(
				message = message,
				actionLabel = "Restore",
				withDismissAction = true,
			)
			if (result == SnackbarResult.ActionPerformed) {
				viewModel.restoreNote()
			}
		}
	}
	var isOrderMenuVisible by remember { mutableStateOf(false) }

	Scaffold(
		topBar = {
			TopAppBar(
				title = { Text(text = "All Notes") },
				actions = {

					Box {
						TextButton(
							modifier = Modifier.width(150.dp),
							onClick = { isOrderMenuVisible = !isOrderMenuVisible },
						) {
							Text(
								text = when (notesOrder) {
									is Order.DateCreated -> "Date Created"
									is Order.DateModified -> "Date Modified"
									is Order.Title -> "Title"
								},
							)
						}
						DropdownMenu(
							expanded = isOrderMenuVisible,
							onDismissRequest = { isOrderMenuVisible = false },
						) {
							DropdownMenuItem(
								text = { Text(text = "Date Created") },
								onClick = {
									viewModel.notesOrderChange(Order.DateCreated(notesOrder.orderType))
									isOrderMenuVisible = false
								},
							)
							DropdownMenuItem(
								text = { Text(text = "Date Modified") },
								onClick = {
									viewModel.notesOrderChange(Order.DateModified(notesOrder.orderType))
									isOrderMenuVisible = false
								},
							)
							DropdownMenuItem(
								text = { Text(text = "Title") },
								onClick = {
									viewModel.notesOrderChange(Order.Title(notesOrder.orderType))
									isOrderMenuVisible = false
								},
							)
						}
					}
					Spacer(modifier = Modifier.width(8.dp))
					IconButton(onClick = viewModel::notesOrderTypeChange) {
						Icon(
							imageVector = when (notesOrder.orderType) {
								OrderType.Ascending -> Icons.Default.ArrowUpward
								OrderType.Descending -> Icons.Default.ArrowDownward
							},
							contentDescription = null,
						)
					}
				}
			)
		},
		floatingActionButton = {
			ExtendedFloatingActionButton(
				text = { Text(text = "ADD") },
				icon = { Icon(imageVector = Icons.Default.Edit, contentDescription = null) },
				onClick = { navController.navigate("addNote") },
			)
		},
		snackbarHost = {
			SnackbarHost(hostState = snackbarHostState)
		}
	) { innerPadding ->
		LazyColumn(
			modifier = Modifier
				.fillMaxSize()
				.padding(innerPadding),
			contentPadding = PaddingValues(
				start = 16.dp,
				top = 16.dp,
				end = 16.dp,
				bottom = 96.dp,
			),
			verticalArrangement = Arrangement.spacedBy(16.dp)
		) {
			items(
				items = notes,
				key = { note -> note.id!! },
			) { note ->
				NoteCard(
					noteTitle = note.title,
					noteContent = note.contents,
					onClick = { viewModel.expandNote(note.id) },
					onLongClick = { navController.navigate("AddNote?noteId=${note.id}") },
					onDeleteClick = { viewModel.deleteNote(note) },
					isExpanded = expandedNoteId == note.id,
				)
			}
		}
	}
}