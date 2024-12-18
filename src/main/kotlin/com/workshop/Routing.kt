package com.workshop

import com.workshop.model.PlayerModel
import com.workshop.model.RoomModel
import com.workshop.model.RoomRepository
import com.workshop.model.TaskRepository
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.sql.Connection
import java.sql.DriverManager
import org.jetbrains.exposed.sql.*

fun Application.configureRouting(repository: RoomRepository) {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause" , status = HttpStatusCode.InternalServerError)
        }
    }
    routing {
        get("/players") {
            call.respond(listOf(
                PlayerModel("Player 1", "1"),
                PlayerModel("Player 2", "2"),
                PlayerModel("Player 3", "3"),
                PlayerModel("Player 4", "4")
                )
            )
        }

        get("/room") {
            val roomName = call.parameters["room"]
            if (roomName.isNullOrBlank()) {
                call.respondText(text = "403: Required parameter is null roomName: $roomName" , status = HttpStatusCode.BadRequest)
            } else {
                val room = repository.getRoomByName(roomName)
                if (room == null ) {
                    call.respondText(text = "204: There is no room with this name roomName: $roomName" , status = HttpStatusCode.NoContent)
                } else {
                    call.respond(room)
                }
            }

        }

        get("/") {
            call.respondText("Hello World!")
        }
        post("/sendvote") {
            val roomName = call.parameters["room"]
            val player = call.parameters["player"]
            val receivedValue = call.receiveText()
            if (roomName.isNullOrBlank() || player.isNullOrBlank()) {
                call.respondText(text = "403: Required parameter is null roomName: $roomName, player: $player" , status = HttpStatusCode.BadRequest)
            } else {
                repository.voteCurrentIssue(
                roomName = roomName,
                playerName = player,
                voteValue = receivedValue)
                val room = repository.getRoomByName(roomName)
                if (room == null ) {
                    call.respondText(text = "204: There is no room with this name roomName: $roomName" , status = HttpStatusCode.NoContent)
                } else {
                    call.respond(room)
                }
            }
        }

        post("/create-room") {
            val roomName = call.parameters["room"]
            val moderator = call.parameters["moderator"]
            if (roomName.isNullOrBlank() || moderator.isNullOrBlank()) {
                call.respondText(text = "403: Required parameter is null roomName: $roomName, moderator: $moderator" , status = HttpStatusCode.BadRequest)
            } else {
                val room = repository.createRoom(roomName, moderator)
                if (room != null) {
                    call.respond(room)
                } else {
                    call.respondText(text = "500: failed to create room: $roomName, moderator: $moderator" , status = HttpStatusCode.InternalServerError)
                }
            }
        }

        post("/join-room") {
            val roomName = call.parameters["room"]
            val player = call.parameters["player"]
            if (roomName.isNullOrBlank() || player.isNullOrBlank()) {
                call.respondText(text = "403: Required parameter is null roomName: $roomName, player: $player" , status = HttpStatusCode.BadRequest)
            } else {
                val room = repository.joinRoom(roomName, player)
                if (room != null) {
                    call.respond(room)
                } else {
                    call.respondText(text = "500: failed to create room: $roomName, player: $player" , status = HttpStatusCode.InternalServerError)
                }
            }
        }
        // Static plugin. Try to access `/static/index.html`
        staticResources("/static", "static")
    }
}
