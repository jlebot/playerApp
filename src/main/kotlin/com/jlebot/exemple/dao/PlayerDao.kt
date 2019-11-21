package com.jlebot.exemple.dao

import com.jlebot.exemple.domain.api.Player
import com.jlebot.exemple.domain.application.IPlayerRepository
import org.skife.jdbi.v2.sqlobject.Bind
import org.skife.jdbi.v2.sqlobject.BindBean
import org.skife.jdbi.v2.sqlobject.SqlQuery
import org.skife.jdbi.v2.sqlobject.SqlUpdate
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper

@RegisterMapper(PlayerMapper::class)
interface PlayerDao : IPlayerRepository {

    @SqlUpdate("INSERT into players (pseudo, points) VALUES (:pseudo, :points)")
    override fun insert(@BindBean player: Player)

    @SqlUpdate("update players set points = :points where pseudo = :pseudo")
    override fun update(@BindBean player: Player)

    @SqlQuery("select pseudo, points from players where pseudo = :pseudo")
    override fun find(@Bind("pseudo") pseudo: String): Player?

    @SqlQuery("select pseudo, points from players order by points desc")
    override fun findAll(): List<Player>

    @SqlUpdate("delete from players where pseudo = :pseudo")
    override fun delete(@BindBean player: Player)

    @SqlUpdate("delete from players")
    override fun deleteAll()

    @SqlQuery("select pseudo, points from players where UPPER(pseudo) like :filter order by points desc limit :limit offset :offset")
    override fun findWithPagination(@Bind("filter") filter: String, @Bind("offset") offset: Int, @Bind("limit") limit: Int): List<Player>

    @SqlQuery("select count(*) from players where UPPER(pseudo) like :filter")
    override fun countPlayersWithFilter(@Bind("filter") filter: String): Int

    @SqlQuery("select 1 + count(*) from players where points > :points")
    override fun getRank(@BindBean player: Player) : Int

}