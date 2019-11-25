# BetclicApp

This project present a list of players and their rank durin a tournament.

Once launch, the api can be access at `http://localhost:8080/` and UI interface can be access at `http://localhost:4200/`

## Server configuration

Java, Node and Angular-CLI need to be install on the server.

The app can be launch by a script  `playerApp.sh` at the root of the project.

## Database configuration

The app have been tested using PostgreSql database but any relational database should work whith the proper configuration and driver dependancy.

The database config file should be edited in 'src/main/resources/config/local.yaml'

The database init script can be found at in 'bdd/init_base.sql'

## Build

Run `./playerApp.sh server install` to build server application.

Run `./playerApp.sh front install` to install front dependancies.

## Run

Run `./playerApp.sh server up` to lauch server service.

Run `./playerApp.sh front up` to lauch the front app.
