package com.ba.simplenotes.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteCard(
	noteTitle: String,
	noteContent: String,
	onClick: () -> Unit,
	onLongClick: () -> Unit,
	onDeleteClick: () -> Unit,
	isExpanded: Boolean,
) {
	Card(
		modifier = Modifier
			.fillMaxWidth()
			.animateContentSize()
			.clip(MaterialTheme.shapes.large)
			.combinedClickable(
				onClick = onClick,
				onLongClick = onLongClick,
			),
		shape = MaterialTheme.shapes.large,
	) {
		Column(
			modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
		) {
			Text(
				text = noteTitle,
				fontSize = 20.sp,
				fontWeight = FontWeight.W500,
				overflow = TextOverflow.Ellipsis,
				maxLines = if (isExpanded) Int.MAX_VALUE else 1,
			)
			Divider(
				modifier = Modifier.padding(vertical = 8.dp),
				thickness = 2.dp,
				color = MaterialTheme.colorScheme.secondary
			)
			Text(
				text = noteContent,
				fontSize = 18.sp,
				overflow = TextOverflow.Ellipsis,
				maxLines = if (isExpanded) Int.MAX_VALUE else 2,
			)
			if (isExpanded) {
				IconButton(
					onClick = onDeleteClick,
					modifier = Modifier
						.padding(top = 4.dp)
						.align(Alignment.End),
					colors = IconButtonDefaults.iconButtonColors(
						contentColor = MaterialTheme.colorScheme.error,
					)
				) {
					Icon(imageVector = Icons.Default.Delete, contentDescription = null)
				}
			}
		}
	}
}
