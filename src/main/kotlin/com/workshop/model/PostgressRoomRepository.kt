package com.workshop.model

class PostgressRoomRepository: RoomRepository {
    override suspend fun getRoomByName(roomName: String): RoomModel {
        TODO("Not yet implemented")
    }

    override suspend fun createRoom(roomName: String, moderatorName: String): String {
        TODO("Not yet implemented")
    }

    override suspend fun joinRoom(roomName: String, playerName: String): String {
        TODO("Not yet implemented")
    }

    override suspend fun voteCurrentIssue(
        roomName: String,
        playerName: String,
        voteValue: String
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun resetVotes(roomName: String) {
        TODO("Not yet implemented")
    }

    override suspend fun nextIssue(roomName: String) {
        TODO("Not yet implemented")
    }
}