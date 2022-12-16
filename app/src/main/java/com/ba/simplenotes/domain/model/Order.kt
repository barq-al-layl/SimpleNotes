package com.ba.simplenotes.domain.model

sealed class Order(val orderType: OrderType) {
	class DateModified(orderType: OrderType) : Order(orderType)
	class DateCreated(orderType: OrderType) : Order(orderType)
	class Title(orderType: OrderType) : Order(orderType)

	fun copy(orderType: OrderType): Order {
		return when (this) {
			is DateCreated -> DateCreated(orderType)
			is DateModified -> DateModified(orderType)
			is Title -> Title(orderType)
		}
	}
}