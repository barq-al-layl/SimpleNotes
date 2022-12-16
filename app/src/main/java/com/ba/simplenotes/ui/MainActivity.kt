package com.ba.simplenotes.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ba.simplenotes.ui.SimpleNotesNavHost
import com.ba.simplenotes.ui.theme.SimpleNotesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			SimpleNotesTheme {
				SimpleNotesNavHost()
			}
		}
	}
}
