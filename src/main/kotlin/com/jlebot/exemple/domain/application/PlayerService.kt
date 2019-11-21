package com.jlebot.exemple.domain.application

import com.jlebot.exemple.Constants
import com.jlebot.exemple.dao.PlayerDao
import com.jlebot.exemple.domain.api.IPlayerApi
import com.jlebot.exemple.domain.api.Player
import com.jlebot.exemple.exception.PlayerNotFoundException
import com.jlebot.exemple.exception.PlayerValidationException
import com.jlebot.exemple.pagination.Page
import org.apache.commons.lang3.StringUtils

class PlayerService(val playerDao: PlayerDao) : IPlayerApi {

    override fun getAllPlayersSortByPoints() = playerDao.findAll()

    override fun getPlayer(pseudo: String) = playerDao.find(pseudo) ?: throw PlayerNotFoundException(pseudo)

    override fun save(player: Player) : Player {
        validate(player)
        val playerInBdd = playerDao.find(player.pseudo)
        if (playerInBdd == null) {
            playerDao.insert(player)
        } else {
            playerDao.update(playerInBdd.pseudo, player.points)
        }
        return player
    }

    override fun getRank(player: Player) = 1

    override fun getPlayersByPage(page: Page) = playerDao.findAllWithPagination(page.pageNumber, page.pageSize)

    private fun validate(player: Player) = if (StringUtils.isNotEmpty(player.pseudo)) true else throw PlayerValidationException(Constants.ERROR_PLAYER_PSEUDO_REQUIRED)

}