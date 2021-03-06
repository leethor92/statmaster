package models

interface GameStore {
    fun findAll(): List<GameModel>
    fun create(game: GameModel) : Long
    fun update(game: GameModel)
    fun delete(game: GameModel)
    fun findById(id:Long) : GameModel?
    fun clear ()
}