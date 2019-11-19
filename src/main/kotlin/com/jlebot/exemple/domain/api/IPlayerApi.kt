package com.jlebot.exemple.domain.api

interface IPlayerApi {

    fun getAllPlayersSortByPoints() : List<Player>
    fun getPlayer(pseudo: String) : Player
    fun getRanking(player: Player) : Int
    fun save(player: Player) : Player

}