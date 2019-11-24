package com.jlebot.exemple.domain.api

import com.jlebot.exemple.pagination.Page

interface IPlayerApi {

    fun getAllPlayersSortByPoints() : List<Player>
    fun getPlayer(pseudo: String) : Player
    fun getRank(player: Player) : Int
    fun create(pseudo: String) : Player
    fun save(player: Player) : Player
    fun getPlayersByPage(page: Page) : List<Player>
    fun countPlayersWithFilter(filter: String): Int
    fun deleteAll()

}