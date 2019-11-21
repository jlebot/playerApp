package com.jlebot.exemple.presentation

import com.jlebot.exemple.domain.api.Player
import com.jlebot.exemple.domain.application.PlayerService
import com.jlebot.exemple.exception.PlayerNotFoundException
import com.jlebot.exemple.exception.PlayerValidationException
import com.jlebot.exemple.pagination.Page
import org.slf4j.LoggerFactory
import javax.validation.Valid
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path(Routes.PLAYER)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class PlayerResource(val playerService: PlayerService) {

    val LOGGER = LoggerFactory.getLogger(PlayerResource::class.java)
    val mapper = PlayerRepresentationMapper(playerService)

    @GET
    fun getPlayersWithOptionalPagination(
                    @QueryParam("filter") filter: String?,
                    @QueryParam("pageNumber") pageNumber: Int?,
                    @QueryParam("pageSize") pageSize: Int?): Response {
        val pagination = pageNumber != null
        return  if (pagination) {
                    LOGGER.info("Handling request to get players with pagination")
                    LOGGER.info("Parameters : pageNumber = {}, pageSize = {}", pageNumber, pageSize)
                    val page = Page(pageNumber ?: 0, pageSize ?: 5)
                    val players = mapper.toRepresentation(playerService.getPlayersByPage(page))
                    Response.ok(players).build()
                } else {
                    LOGGER.info("Handling request to get players")
                    val playersSortByPoints = mapper.toRepresentation(playerService.getAllPlayersSortByPoints())
                    Response.ok(playersSortByPoints).build()
                }
    }

    @GET
    @Path("{pseudo}")
    fun get(@PathParam("pseudo") pseudo: String): Response {
        LOGGER.info("Handling request to get player {}", pseudo)
        return try {
                    val player = mapper.toRepresentation(playerService.getPlayer(pseudo))
                    Response.ok(player).build()
                } catch (e: PlayerNotFoundException) {
                    LOGGER.error("Player with pseudo: {} was not found", pseudo)
                    Response.status(Response.Status.NOT_FOUND).build()
                }
    }

    @PUT
    fun create(@Valid pseudo: String): Response {
        LOGGER.info("Handling request to create player {}", pseudo)
        return try {
                    val player = mapper.toRepresentation(playerService.save(Player(pseudo,0)))
                    Response.ok(player).build()
                } catch (e: PlayerValidationException) {
                    LOGGER.error("Error while creating player {}", pseudo)
                    Response.status(Response.Status.NOT_ACCEPTABLE).build()
                }
    }

    @POST
    fun save(@Valid player: PlayerRepresentation): Response {
        LOGGER.info("Handling request to save player {}", player)
        return try {
                    val playerSaved = mapper.toRepresentation(playerService.save(mapper.toDomain(player)))
                    Response.ok(playerSaved).build()
                } catch (e: PlayerValidationException) {
                    LOGGER.error("Error while saving player {}", player)
                    Response.status(Response.Status.NOT_ACCEPTABLE).build()
                }
    }

}