package com.workshop.model

import kotlinx.serialization.Serializable

@Serializable
data class PlayerModel(val name: String, val point: String = "?")