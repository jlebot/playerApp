package com.jlebot.exemple.dao

import com.jlebot.exemple.domain.api.Player
import org.skife.jdbi.v2.StatementContext
import org.skife.jdbi.v2.tweak.ResultSetMapper
import java.sql.ResultSet

class PlayerMapper : ResultSetMapper<Player> {
    override fun map(index: Int, r: ResultSet, ctx: StatementContext?): Player {
        return Player(r.getString("pseudo"), r.getInt("points"))
    }
}
