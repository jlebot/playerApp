package com.jlebot.exemple.domain.application

import com.jlebot.exemple.domain.api.Player

interface IPlayerRpository {

    fun getAllPlayersSortByPoints() : List<Player>
    fun findForPseudo(pseudo: String) : Player?
    fun save(player: Player) : Player

}