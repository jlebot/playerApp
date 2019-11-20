package com.jlebot.exemple.domain.application

import com.jlebot.exemple.domain.api.Player
import com.jlebot.exemple.pagination.Page

interface IPlayerRpository {

    fun getAllPlayersSortByPoints() : List<Player>
    fun findForPseudo(pseudo: String) : Player?
    fun save(player: Player) : Player
    fun getPlayersByPage(page: Page) : List<Player>

}