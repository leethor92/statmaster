package models

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

var lastPId = 0L

internal fun getPId(): Long {
    return lastPId++
}

class PlayerMemStore : PlayerStore, AnkoLogger {

    val players = ArrayList<PlayerModel>()

    override fun findAll(): List<PlayerModel> {
        return players
    }

    override fun create(player: PlayerModel) {
        player.id = getPId()
        players.add(player)
        logAll()
    }

    override fun update(player: PlayerModel) {
        var foundPlayer: PlayerModel? = players.find { p -> p.id == player.id }
        if (foundPlayer != null) {
            foundPlayer.name = player.name
            foundPlayer.number = player.number
            logAll()
        }
    }

    fun logAll() {
        players.forEach{ info("${it}") }
    }
}