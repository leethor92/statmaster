package models.room

import android.content.Context
import androidx.room.Room
import models.GameModel
import models.GameStore


class GameStoreRoom(val context: Context) : GameStore {

    var dao: GameDao

    init {
        val database = Room.databaseBuilder(context, Database::class.java, "room_sample.db")
            .fallbackToDestructiveMigration()
            .build()
        dao = database.gameDao()
    }

    override fun findAll(): List<GameModel> {
        return dao.findAll()
    }

    override fun findById(id: Long): GameModel? {
        return dao.findById(id)
    }

    override fun create(game: GameModel) {
        dao.create(game)
    }

    override fun update(game: GameModel) {
        dao.update(game)
    }

    override fun delete(game: GameModel) {
        dao.deleteGame(game)
    }

    override fun clear() {
    }
}