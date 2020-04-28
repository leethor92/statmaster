package models

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class GameMemStore : GameStore, AnkoLogger {

    val games = ArrayList<GameModel>()

    override fun findAll(): List<GameModel> {
        return games
    }

    override fun findById(id:Long) : GameModel? {
        val foundGame: GameModel? = games.find { it.id == id }
        return foundGame
    }

    override fun create(game: GameModel) {
        game.id = getId()
        games.add(game)
        logAll();
    }

    override fun update(game: GameModel) {
        var foundGame: GameModel? = games.find { g -> g.id == game.id }
        if (foundGame != null) {
            foundGame.title = game.title
            foundGame.score = game.score
            foundGame.win = game.win
            logAll()
        }
    }

    override fun delete(game: GameModel) {
        games.remove(game)
    }

    fun logAll() {
        games.forEach{ info("${it}") }
    }

    override fun clear() {
        games.clear()
    }
}