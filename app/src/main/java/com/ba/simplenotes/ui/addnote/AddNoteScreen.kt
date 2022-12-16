package com.ba.simplenotes.ui.addnote

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteScreen(
	navController: NavController,
	viewModel: AddNoteViewModel = hiltViewModel(),
) {
	val title by viewModel.noteTitle.collectAsState()
	val contents by viewModel.noteContent.collectAsState()

	val focusManager = LocalFocusManager.current
	val focusRequester = remember { FocusRequester() }
	val scrollState = rememberScrollState(initial = Int.MAX_VALUE)
	val textFieldValue = remember {
		derivedStateOf {
			TextFieldValue(
				text = contents,
				selection = TextRange(start = contents.length, end = contents.length),
			)
		}
	}

	LaunchedEffect(Unit) {
		focusRequester.requestFocus()
	}

	Scaffold(
		topBar = {
			TopAppBar(
				title = {
					val textStyle = LocalTextStyle.current
					TextField(
						value = title,
						onValueChange = viewModel::onNoteTitleChange,
						placeholder = {
							Text(
								text = "Title",
								style = textStyle,
								color = MaterialTheme.colorScheme.onBackground.copy(alpha = .6f),
							)
						},
						keyboardOptions = KeyboardOptions(
							capitalization = KeyboardCapitalization.Sentences,
							imeAction = ImeAction.Next,
						),
						keyboardActions = KeyboardActions(
							onNext = {
								focusManager.moveFocus(FocusDirection.Down)
							},
						),
						colors = TextFieldDefaults.textFieldColors(
							containerColor = Color.Transparent,
							focusedIndicatorColor = Color.Transparent,
							unfocusedIndicatorColor = Color.Transparent,
						),
					)
				},
				navigationIcon = {
					IconButton(onClick = { navController.popBackStack() }) {
						Icon(
							imageVector = Icons.Default.ArrowBackIos,
							contentDescription = null,
						)
					}
				},
				actions = {
					IconButton(
						onClick = {
							viewModel.saveNote()
							navController.popBackStack()
						},
					) {
						Icon(
							imageVector = Icons.Default.Save,
							contentDescription = null,
						)
					}
				}
			)
		},
	) { innerPadding ->
		TextField(
			value = textFieldValue.value,
			onValueChange = { viewModel.onNoteContentChange(it.text) },
			modifier = Modifier
				.fillMaxSize()
				.padding(innerPadding)
				.focusRequester(focusRequester)
				.verticalScroll(state = scrollState),
			keyboardOptions = KeyboardOptions(
				capitalization = KeyboardCapitalization.Sentences,
			),
			colors = TextFieldDefaults.textFieldColors(
				containerColor = Color.Transparent,
				focusedIndicatorColor = Color.Transparent,
				unfocusedIndicatorColor = Color.Transparent,
			),
		)
	}
}