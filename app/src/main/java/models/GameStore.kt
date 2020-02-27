package models

interface GameStore {
    fun findAll(): List<GameModel>
    fun create(game: GameModel)
    fun update(game: GameModel)
    fun delete(game: GameModel)
}