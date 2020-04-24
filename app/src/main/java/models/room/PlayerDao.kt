package models.room

import androidx.room.*
import models.PlayerModel

@Dao
interface PlayerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun create(player: PlayerModel)

    @Query("SELECT * FROM PlayerModel")
    fun findAll(): List<PlayerModel>

    @Query("select * from PlayerModel where id = :id")
    fun findById(id: Long): PlayerModel

    @Update
    fun update(player: PlayerModel)

    @Delete
    fun deletePlayer(player: PlayerModel)
}