package models.firebase

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import models.TeamModel
import models.TeamStore
import org.jetbrains.anko.AnkoLogger

class TeamFireStore(val context: Context) : TeamStore, AnkoLogger {

  val teams = ArrayList<TeamModel>()
  lateinit var userId: String
  lateinit var db: DatabaseReference

  override fun findAll(): List<TeamModel> {
    return teams
  }

  override fun findById(id: Long): TeamModel? {
    val foundTeam: TeamModel? = teams.find { t -> t.id == id }
    return foundTeam
  }

  override fun create(team: TeamModel) : Long {
    val key = db.child("users").child(userId).child("teams").push().key
    //key?.let {
    team.fbId = key!!
    team.id = key.hashCode().toLong()
    teams.add(team)
    db.child("users").child(userId).child("teams").child(key).setValue(team)
    return team.id
    //}
  }

  override fun update(team: TeamModel) {
    var foundTeam: TeamModel? = teams.find { t -> t.fbId == team.fbId }
    if (foundTeam != null) {
      foundTeam.name = team.name
      foundTeam.players = team.players
    }

    db.child("users").child(userId).child("teams").child(team.fbId).setValue(team)

  }

  override fun delete(team: TeamModel) {
    db.child("users").child(userId).child("teams").child(team.fbId).removeValue()
    teams.remove(team)
  }

  override fun clear() {
    teams.clear()
  }

  fun fetchTeams(teamsReady: () -> Unit) {
    val valueEventListener = object : ValueEventListener {
      override fun onCancelled(dataSnapshot: DatabaseError) {
      }
      override fun onDataChange(dataSnapshot: DataSnapshot) {
        dataSnapshot.children.mapNotNullTo(teams) { it.getValue<TeamModel>(TeamModel::class.java) }
        teamsReady()
      }
    }
    userId = FirebaseAuth.getInstance().currentUser!!.uid
    db = FirebaseDatabase.getInstance().reference
    teams.clear()
    db.child("users").child(userId).child("teams").addListenerForSingleValueEvent(valueEventListener)
  }
}