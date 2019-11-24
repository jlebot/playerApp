# BetclicApp

This project present a list of players and their rank durin a tournament.
Once launch, the api can be access at `http://localhost:8080/` and UI interface can be access at `http://localhost:4200/`

## Server configuration

Java need to be install on the server and Node also.
The app can be launch by a script  `playerApp.sh` at the root of the project.

## Database configuration

The app have been tested using PostgreSql database but any relational database should work.
The database config file should be edited in 'src/main/resources/config/local.yaml'
The database init script can be found at in 'bdd/init_base.sql'

## Build

Run `playerApp.sh front install` to install front dependancies.

## Run

Run `playerApp.sh all up` to lauch server service and the front app.
