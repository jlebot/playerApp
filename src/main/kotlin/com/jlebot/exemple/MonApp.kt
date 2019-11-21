package com.jlebot.exemple

import com.jlebot.exemple.config.MonAppConfig
import com.jlebot.exemple.dao.PlayerDao
import com.jlebot.exemple.domain.application.PlayerService
import com.jlebot.exemple.resource.MonAppResource
import com.jlebot.exemple.resource.PlayerResource
import io.dropwizard.Application
import io.dropwizard.db.DataSourceFactory
import io.dropwizard.jdbi.DBIFactory
import io.dropwizard.migrations.MigrationsBundle
import io.dropwizard.setup.Bootstrap
import io.dropwizard.setup.Environment
import org.eclipse.jetty.servlets.CrossOriginFilter
import java.util.*
import javax.servlet.DispatcherType

class MonApp : Application<MonAppConfig>() {

    override fun run(configuration: MonAppConfig, environment: Environment) {
        println("Running betclic ranking app !")

        // Configure database
        val jdbi = DBIFactory().build(environment, configuration.dataSourceFactory, "postgresql")
        val playerDao = jdbi.onDemand(PlayerDao::class.java)

        // Configure resources
        environment.jersey().register(MonAppResource())
        environment.jersey().register(PlayerResource(PlayerService(playerDao)))

        // Configure CORS for local communication
        val cors = environment.servlets().addFilter("CORS", CrossOriginFilter::class.java)
        cors.setInitParameter("allowedOrigins", "*")
        cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin")
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD")
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType::class.java), true, "/*")
    }

    override fun initialize(bootstrap: Bootstrap<MonAppConfig>) {
        bootstrap.addBundle(object: MigrationsBundle<MonAppConfig>() {
            override fun getDataSourceFactory(configuration: MonAppConfig): DataSourceFactory {
                return configuration.dataSourceFactory
            }
        })
    }

}