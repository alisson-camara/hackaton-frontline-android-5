package com.workshop.model

class FakeRoomRepository: RoomRepository {
    private val rooms = mutableListOf(
        RoomModel(
            name = "default", currentTask = "Task 1",
            moderator = "default",
            players = mutableListOf(PlayerModel(name = "default")),
        )
    )
    override suspend fun getRoomByName(roomName: String): RoomModel? {
        return rooms.firstOrNull { it.name == roomName }
    }

    override suspend fun createRoom(roomName: String, moderatorName: String): RoomModel? {
        val newRoom = RoomModel(name = roomName,
            moderator = moderatorName,
            currentTask = "Task 1",
            players = mutableListOf(PlayerModel(name = moderatorName))
        )
        val existingRoom = rooms.find { it.name == roomName }
        if (existingRoom == null) {
            rooms.add(newRoom)
            return newRoom
        } else {
            return null
        }
    }

    override suspend fun joinRoom(roomName: String, playerName: String): RoomModel? {
        val room = getRoomByName(roomName) ?: return null
        val player = room.players.firstOrNull { it.name == playerName }
        if (player == null) {
            room.players.add(PlayerModel(name = playerName))
            return room
        } else {
            return null
        }


    }

    override suspend fun voteCurrentIssue(
        roomName: String,
        playerName: String,
        voteValue: String
    ) {
         getRoomByName(roomName)?.players?.first { it.name == playerName }?.point = voteValue
    }

    override suspend fun resetVotes(roomName: String) {
        getRoomByName(roomName)?.players?.forEach { it.point = "?" }
    }

    override suspend fun nextIssue(roomName: String) {
        resetVotes(roomName)
        val currentTaskName = getRoomByName(roomName)?.currentTask
        val nextTaskName = "Task ${currentTaskName?.last()?.digitToInt()?.plus(1)}"
        getRoomByName(roomName)?.currentTask = "nextTaskName"
    }

}