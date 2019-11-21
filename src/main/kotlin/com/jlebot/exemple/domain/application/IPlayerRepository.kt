package com.jlebot.exemple.domain.application

import com.jlebot.exemple.domain.api.Player

interface IPlayerRepository {

    fun insert(player: Player) : Int
    fun update(pseudo: String, points: Int) : Int
    fun find(pseudo: String): Player?
    fun findAll(): List<Player>
    fun delete(pseudo: String) : Int
    fun deleteAll() : Int
    fun findWithPagination(offset: Int, limit: Int): List<Player>

}