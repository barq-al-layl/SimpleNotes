package com.ba.simplenotes.domain.model

data class Note(
	val id: Int?,
	val dateCreated: Long,
	val dateModified: Long,
	val title: String,
	val contents: String,
)
