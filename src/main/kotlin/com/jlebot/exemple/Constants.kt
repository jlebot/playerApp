package com.jlebot.exemple


class Constants private constructor() {
    companion object {
        const val MESSAGE_WELCOME = "Welcome to Openclassrooms brand new server !"

        // Error messages
        const val ERROR_PLAYER_PSEUDO_REQUIRED = "Pseudo required"
        const val ERROR_PLAYER_PSEUDO_SIZE_LIMIT = "Pseudo lenght is {}, it exceed the limit of 10"
        const val ERROR_PLAYER_PSEUDO_ALPHANUMERIC = "Pseudo must countain only alphanumeric characters"
        const val ERROR_PLAYER_NOT_FOUND = "Player with id '{}' not found"
    }
}