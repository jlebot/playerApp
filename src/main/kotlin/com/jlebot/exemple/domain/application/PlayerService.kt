package com.jlebot.exemple.domain.application

import com.jlebot.exemple.Constants
import com.jlebot.exemple.domain.api.IPlayerApi
import com.jlebot.exemple.domain.api.Player
import com.jlebot.exemple.exception.PlayerNotFoundException
import com.jlebot.exemple.exception.PlayerValidationException
import com.jlebot.exemple.pagination.Page
import org.apache.commons.lang3.StringUtils

class PlayerService(val playerRepository: IPlayerRepository) : IPlayerApi {

    override fun getAllPlayersSortByPoints() = playerRepository.findAll()

    override fun getPlayer(pseudo: String) = playerRepository.find(pseudo) ?: throw PlayerNotFoundException(pseudo)

    override fun save(player: Player) : Player {
        validate(player)
        val playerInBdd = playerRepository.find(player.pseudo)
        if (playerInBdd == null) {
            playerRepository.insert(player)
        } else {
            playerRepository.update(player)
        }
        return player
    }

    override fun getRank(player: Player) = playerRepository.getRank(player)

    override fun getPlayersByPage(page: Page) = playerRepository.findWithPagination(page.pageNumber * page.pageSize, page.pageSize)

    override fun countPlayers() = playerRepository.count()

    override fun deleteAll() = playerRepository.deleteAll()

    private fun validate(player: Player) {
        if (StringUtils.isEmpty(player.pseudo)) {
            throw PlayerValidationException(Constants.ERROR_PLAYER_PSEUDO_REQUIRED)
        } else if (player.pseudo.length > 10) {
            throw PlayerValidationException(Constants.ERROR_PLAYER_PSEUDO_SIZE_LIMIT)
        } else if (!StringUtils.isAlphanumeric(player.pseudo)) {
            throw PlayerValidationException(Constants.ERROR_PLAYER_PSEUDO_ALPHANUMERIC)
        }
    }

}