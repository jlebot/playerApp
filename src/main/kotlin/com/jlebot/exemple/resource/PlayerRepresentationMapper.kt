package com.jlebot.exemple.resource

import com.google.common.collect.Lists
import com.jlebot.exemple.domain.api.IPlayerApi
import com.jlebot.exemple.domain.api.Player
import com.jlebot.exemple.pagination.Page

class PlayerRepresentationMapper(val playerApi: IPlayerApi) {

    fun toRepresentation(player: Player) = PlayerRepresentation(player.pseudo, player.points, playerApi.getRank(player))

    fun toRepresentation(playersSortByPoints: List<Player>) = toRepresentation(playersSortByPoints, null)

    fun toRepresentation(playersSortByPoints: List<Player>, page: Page?) : List<PlayerRepresentation> {
        val result = Lists.newArrayList<PlayerRepresentation>()
        var counter = if (page == null) 1 else page.pageNumber * page.pageSize + 1
        var rank = counter
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