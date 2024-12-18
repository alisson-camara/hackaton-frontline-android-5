package com.workshop.model

import kotlinx.serialization.Serializable

@Serializable
data class RoomModel(
    val name: String,
    var currentTask: String,
    val moderator: String,
    val players: MutableList<PlayerModel> = mutableListOf()
)