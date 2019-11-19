package com.jlebot.exemple.presentation

import com.jlebot.exemple.Constants
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
class MonAppResource {

    @GET
    fun identity(): String = Constants.MESSAGE_WELCOME

}