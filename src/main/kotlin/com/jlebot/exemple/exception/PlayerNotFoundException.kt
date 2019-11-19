package com.jlebot.exemple.exception

import com.jlebot.exemple.Constants

class PlayerNotFoundException : Exception {
    constructor(pseudo: String?) : super(String.format(Constants.ERROR_PLAYER_NOT_FOUND, pseudo))
}