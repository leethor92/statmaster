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

val PlayerJSON_FILE = "players.json"
val playergsonBuilder = GsonBuilder().setPrettyPrinting().create()
val playerlistType = object : TypeToken<ArrayList<PlayerModel>>() {}.type

fun generateRandomPlayerId(): Long {
    return Random().nextLong()
}

class PlayerJSONStore : PlayerStore, AnkoLogger {

    val context: Context
    var players = mutableListOf<PlayerModel>()

    constructor (context: Context) {
        this.context = context
        if (exists(context, PlayerJSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<PlayerModel> {
        return players
    }

    override fun create(player: PlayerModel) {
        player.id = generateRandomId()
        players.add(player)
        serialize()
    }


    override fun update(player: PlayerModel) {
        val playersList = findAll() as ArrayList<PlayerModel>
        var foundPlayer: PlayerModel? = playersList.find { p -> p.id == player.id }
        if (foundPlayer != null) {
            foundPlayer.name = player.name
            foundPlayer.number = player.number
            foundPlayer.image = player.image
            foundPlayer.point = player.point
            foundPlayer.goal = player.goal
            foundPlayer.wide = player.wide
            foundPlayer.pass = player.pass
            foundPlayer.possession = player.possession
        }
        serialize()
    }

    override fun delete(player: PlayerModel) {
        players.remove(player)
        serialize()
    }

    private fun serialize() {
        val jsonString = playergsonBuilder.toJson(players, playerlistType)
        write(context, PlayerJSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, PlayerJSON_FILE)
        players = Gson().fromJson(jsonString, playerlistType)
    }
}