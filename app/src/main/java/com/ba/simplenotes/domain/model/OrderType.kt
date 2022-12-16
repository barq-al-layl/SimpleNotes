package com.ba.simplenotes.domain.model

sealed class OrderType {
	object Ascending : OrderType()
	object Descending : OrderType()
}
