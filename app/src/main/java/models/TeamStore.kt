package models

interface TeamStore {
  fun findAll(): List<TeamModel>
  fun create(team: TeamModel) : Long
  fun update(team: TeamModel)
  fun delete(team: TeamModel)
  fun findById(id:Long) : TeamModel?
  fun clear ()
}