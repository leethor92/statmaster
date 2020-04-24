package models.room

import androidx.room.*
import models.GameModel

@Dao
interface GameDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun create(game: GameModel)

    @Query("SELECT * FROM GameModel")
    fun findAll(): List<GameModel>

    @Query("select * from GameModel where id = :id")
    fun findById(id: Long): GameModel

    @Update
    fun update(game: GameModel)

    @Delete
    fun deleteGame(game: GameModel)
}