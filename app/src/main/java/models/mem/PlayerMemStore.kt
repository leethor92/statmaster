package models

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

var lastPId = 0L

internal fun getPId(): Long {
    return lastPId++
}

class PlayerMemStore : PlayerStore, AnkoLogger {

    val players = ArrayList<PlayerModel>()

    override fun findAll(): ArrayList<PlayerModel> {
        return players
    }

    override fun findById(id:Long) : PlayerModel? {
        val foundPlayer: PlayerModel? = players.find { it.id == id }
        return foundPlayer
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
            foundPlayer.image = player.image
            foundPlayer.point = player.point
            foundPlayer.goal = player.goal
            foundPlayer.wide = player.wide
            foundPlayer.pass = player.pass
            foundPlayer.possession = player.possession
            logAll()
        }
    }

    override fun delete(player: PlayerModel) {
        players.remove(player)
    }

    fun logAll() {
        players.forEach{ info("${it}") }
    }

    override fun clear() {
        players.clear()
    }
}