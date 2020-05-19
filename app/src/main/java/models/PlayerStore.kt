package models

interface PlayerStore {
    fun findAll(): MutableList<PlayerModel>
    fun create(player: PlayerModel) : Long
    fun update(player: PlayerModel)
    fun delete(player: PlayerModel)
    fun findById(id:Long) : PlayerModel?
    fun clear ()
}