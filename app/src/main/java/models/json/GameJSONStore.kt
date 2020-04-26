package models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import helpers.exists
import helpers.read
import helpers.write
import org.jetbrains.anko.AnkoLogger
import java.util.*

val JSON_FILE = "games.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<ArrayList<GameModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class GameJSONStore : GameStore, AnkoLogger {

    val context: Context
    var games = mutableListOf<GameModel>()

    constructor (context: Context) {
        this.context = context
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<GameModel> {
        return games
    }

    override fun findById(id:Long) : GameModel? {
        val foundGame: GameModel? = games.find { it.id == id }
        return foundGame
    }

    override fun create(game: GameModel) {
        game.id = generateRandomId()
        games.add(game)
        serialize()
    }


    override fun update(game: GameModel) {
        val gamesList = findAll() as ArrayList<GameModel>
        var foundGame: GameModel? = gamesList.find { g -> g.id == game.id }
        if (foundGame != null) {
            foundGame.title = game.title
            foundGame.score = game.score
            foundGame.win = game.win
        }
        serialize()
    }

    override fun delete(game: GameModel) {
        games.remove(game)
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(games, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        games = Gson().fromJson(jsonString, listType)
    }
}