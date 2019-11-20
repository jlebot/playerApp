package com.jlebot.exemple.presentation

import com.google.common.collect.Lists
import com.jlebot.exemple.domain.api.Player
import com.jlebot.exemple.domain.application.PlayerService

object PlayerRepresentationMapper {

    fun toRepresentation(player: Player) = PlayerRepresentation(player.pseudo, player.points, PlayerService.getRank(player))

    fun toRepresentation(playersSortByPoints: List<Player>) : List<PlayerRepresentation> {
        val result = Lists.newArrayList<PlayerRepresentation>()
        var rank = 1
        var counter = 1
        var previousPoints = -1
        for (player in playersSortByPoints) {
            rank = if (player.points == previousPoints) rank else counter
            result.add(PlayerRepresentation(player.pseudo, player.points, rank))
            counter ++
            previousPoints = player.points
        }
        return result
    }

    fun toDomain(player: PlayerRepresentation) = Player(player.pseudo ?: "", player.points ?: 0)

}