package models

interface PlayerStore {
    fun findAll(): List<PlayerModel>
    fun create(player: PlayerModel)
    fun update(player: PlayerModel)
    fun delete(player: PlayerModel)
    fun findById(id:Long) : PlayerModel?
}