package com.jlebot.exemple.presentation

import com.jlebot.exemple.domain.api.Player
import com.jlebot.exemple.domain.application.PlayerService
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Path(Routes.PLAYER)
@Produces(MediaType.APPLICATION_JSON)
class PlayerResource {

    @GET
    fun getPlayersSortByPoints(): List<PlayerRepresentation> = PlayerRepresentationMapper.toRepresentation(PlayerService.getAllPlayersSortByPoints())

    @GET
    @Path("{pseudo}")
    fun get(@PathParam("pseudo") pseudo: String): PlayerRepresentation = PlayerRepresentationMapper.toRepresentation(PlayerService.getPlayer(pseudo))

    @POST
    fun save(): PlayerRepresentation = PlayerRepresentationMapper.toRepresentation(PlayerService.save(Player("titi",5)))

}