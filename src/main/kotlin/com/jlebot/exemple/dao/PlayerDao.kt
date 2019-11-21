package com.jlebot.exemple.dao

import com.jlebot.exemple.domain.api.Player
import com.jlebot.exemple.domain.application.IPlayerRpository
import org.skife.jdbi.v2.sqlobject.Bind
import org.skife.jdbi.v2.sqlobject.BindBean
import org.skife.jdbi.v2.sqlobject.SqlQuery
import org.skife.jdbi.v2.sqlobject.SqlUpdate
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper

@RegisterMapper(PlayerMapper::class)
interface PlayerDao : IPlayerRpository {

    @SqlUpdate("INSERT into players (pseudo, points) VALUES (:pseudo, :points)")
    fun insert(@BindBean player: Player) : Int

    @SqlUpdate("update players set points = :points where pseudo = :pseudo")
    fun update(@Bind("pseudo") pseudo: String, @Bind("points") points: Int) : Int

    @SqlQuery("select pseudo, points from players where pseudo = :pseudo")
    fun find(@Bind("pseudo") pseudo: String): Player?

    @SqlQuery("select pseudo, points from players order by points")
    fun findAll(): List<Player>

    @SqlUpdate("delete from players where pseudo = :pseudo")
    fun delete(@Bind("pseudo") pseudo: String) : Int

    @SqlUpdate("delete from players")
    fun deleteAll() : Int

    @SqlQuery("select pseudo, points from players order by points limit :pageSize offset :pageNumber")
    fun findAllWithPagination(@Bind("pageNumber") pageNumber: Int, @Bind("pageSize") pageSize: Int): List<Player>

}