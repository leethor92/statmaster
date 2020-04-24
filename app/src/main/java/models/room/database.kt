package models.room

import androidx.room.Database
import androidx.room.RoomDatabase
import models.GameModel
import models.PlayerModel

@Database(entities = arrayOf(GameModel::class, PlayerModel::class), version = 1,  exportSchema = false)
abstract class Database : RoomDatabase() {

    abstract fun gameDao(): GameDao

    abstract fun playerDao(): PlayerDao
}