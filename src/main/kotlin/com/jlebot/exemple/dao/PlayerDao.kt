package com.jlebot.exemple.dao

import com.jlebot.exemple.domain.api.Player
import com.jlebot.exemple.domain.application.IPlayerRpository

object PlayerDao : IPlayerRpository {

    private val players: List<Player> = listOf(
        Player("M3rzhin", 50000),
        Player("Titi", 77),
        Player("Tata", 77),
        Player("Toto", 450),
        Player("Looser", 2)
    )

    override fun getAllPlayersSortByPoints() = players.sortedByDescending { player -> player.points }

    override fun findForPseudo(pseudo: String): Player? = players.find { player -> player.pseudo.equals(pseudo) }

    override fun save(player: Player) = player

}