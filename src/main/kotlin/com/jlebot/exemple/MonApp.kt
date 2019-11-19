package com.jlebot.exemple

import com.jlebot.exemple.config.MonAppConfig
import com.jlebot.exemple.presentation.MonAppResource
import com.jlebot.exemple.presentation.PlayerResource
import io.dropwizard.Application
import io.dropwizard.setup.Environment

class MonApp : Application<MonAppConfig>() {
    override fun run(configuration: MonAppConfig, environment: Environment) {
        println("Running ${configuration.name}!")
        val monAppResource = MonAppResource()
        environment.jersey().register(monAppResource)
        val playerResource = PlayerResource()
        environment.jersey().register(playerResource)
    }
}