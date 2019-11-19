package com.jlebot.exemple.domain.application

import com.jlebot.exemple.Constants
import com.jlebot.exemple.dao.PlayerDao
import com.jlebot.exemple.domain.api.IPlayerApi
import com.jlebot.exemple.domain.api.Player
import com.jlebot.exemple.exception.PlayerNotFoundException
import com.jlebot.exemple.exception.PlayerValidationException
import org.apache.commons.lang3.StringUtils

object PlayerService : IPlayerApi {

    override fun getAllPlayersSortByPoints() = PlayerDao.getAllPlayersSortByPoints()

    override fun getPlayer(pseudo: String) = PlayerDao.findForPseudo(pseudo) ?: throw PlayerNotFoundException(pseudo)

    override fun save(player: Player) : Player {
        validate(player)
        val playerInBdd = PlayerDao.findForPseudo(player.pseudo) ?: player
        playerInBdd.points = player.points
        return playerInBdd
    }

    override fun getRanking(player: Player) = 1

    private fun validate(player: Player) = if (StringUtils.isNotEmpty(player.pseudo)) true else throw PlayerValidationException(Constants.ERROR_PLAYER_PSEUDO_REQUIRED)

}