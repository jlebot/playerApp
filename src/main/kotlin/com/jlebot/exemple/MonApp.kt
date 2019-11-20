package com.jlebot.exemple

import com.jlebot.exemple.config.MonAppConfig
import com.jlebot.exemple.presentation.MonAppResource
import com.jlebot.exemple.presentation.PlayerResource
import io.dropwizard.Application
import io.dropwizard.setup.Environment
import org.eclipse.jetty.servlets.CrossOriginFilter
import java.util.*
import javax.servlet.DispatcherType


class MonApp : Application<MonAppConfig>() {
    override fun run(configuration: MonAppConfig, environment: Environment) {
        println("Running ${configuration.name}!")

        // Declare resources
        val monAppResource = MonAppResource()
        environment.jersey().register(monAppResource)
        val playerResource = PlayerResource()
        environment.jersey().register(playerResource)

        // Configure CORS for local communication
        val cors = environment.servlets().addFilter("CORS", CrossOriginFilter::class.java)
        cors.setInitParameter("allowedOrigins", "*")
        cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin")
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD")
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType::class.java), true, "/*")
    }
}