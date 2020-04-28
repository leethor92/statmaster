package models.room

import android.content.Context
import androidx.room.Room
import models.PlayerModel
import models.PlayerStore

class PlayerStoreRoom(val context: Context) : PlayerStore {

    var dao: PlayerDao

    init {
        val database = Room.databaseBuilder(context, Database::class.java, "room_sample.db")
            .fallbackToDestructiveMigration()
            .build()
        dao = database.playerDao()
    }

    override fun findAll(): List<PlayerModel> {
        return dao.findAll()
    }

    override fun findById(id: Long): PlayerModel? {
        return dao.findById(id)
    }

    override fun create(player: PlayerModel) {
        dao.create(player)
    }

    override fun update(player: PlayerModel) {
        dao.update(player)
    }

    override fun delete(player: PlayerModel) {
        dao.deletePlayer(player)
    }

    override fun clear() {
    }
}