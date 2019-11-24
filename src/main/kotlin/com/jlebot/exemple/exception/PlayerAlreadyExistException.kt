package com.jlebot.exemple.exception

import com.jlebot.exemple.Constants

class PlayerAlreadyExistException : Exception {
    constructor(pseudo: String?) : super(String.format(Constants.ERROR_PLAYER_ALREADY_EXIST, pseudo))
}