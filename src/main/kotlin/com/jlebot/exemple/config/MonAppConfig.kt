package com.jlebot.exemple.config

import com.fasterxml.jackson.annotation.JsonProperty
import io.dropwizard.Configuration
import io.dropwizard.db.DataSourceFactory

class MonAppConfig(@JsonProperty("database") val dataSourceFactory: DataSourceFactory) : Configuration()