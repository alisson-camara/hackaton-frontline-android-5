package com.workshop.model

interface RoomRepository {
    suspend fun getRoomByName(roomName: String): RoomModel?
    suspend fun createRoom(roomName: String, moderatorName: String): RoomModel?
    suspend fun joinRoom(roomName: String, playerName: String): RoomModel?
    suspend fun voteCurrentIssue(roomName: String, playerName: String, voteValue: String)
    suspend fun resetVotes(roomName: String)
    suspend fun nextIssue(roomName: String)
}