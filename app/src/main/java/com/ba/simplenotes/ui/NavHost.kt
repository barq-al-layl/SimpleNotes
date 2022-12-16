package com.ba.simplenotes.ui

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ba.simplenotes.ui.addnote.AddNoteScreen
import com.ba.simplenotes.ui.notes.NotesScreen

@Composable
fun SimpleNotesNavHost() {
	val navController = rememberNavController()

	NavHost(
		navController = navController,
		startDestination = "notes",
		modifier = Modifier.background(MaterialTheme.colorScheme.background)
	) {
		composable(route = "notes") {
			NotesScreen(navController)
		}
		composable(
			route = "addNote?noteId={noteId}",
			arguments = listOf(
				navArgument(name = "noteId") {
					type = NavType.IntType
					defaultValue = -1

				},
			),
		) {
			AddNoteScreen(navController)
		}
	}
}