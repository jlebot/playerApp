package com.jlebot.exemple.domain.application

import com.jlebot.exemple.domain.api.Player

interface IPlayerRepository {

    fun insert(player: Player)
    fun update(player: Player)
    fun find(pseudo: String): Player?
    fun findAll(): List<Player>
    fun delete(player: Player)
    fun deleteAll()
    fun findWithPagination(filter: String, offset: Int, limit: Int): List<Player>
    fun countPlayersWithFilter(filter: String): Int
    fun getRank(player: Player) : Int

}