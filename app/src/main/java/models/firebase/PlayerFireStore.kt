package models.firebase

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import models.PlayerModel
import models.PlayerStore
import org.jetbrains.anko.AnkoLogger

class PlayerFireStore(val context: Context) : PlayerStore, AnkoLogger {

    val players = ArrayList<PlayerModel>()
    lateinit var userId: String
    lateinit var db: DatabaseReference

    override fun findAll(): List<PlayerModel> {
        return players
    }

    override fun findById(id: Long): PlayerModel? {
        val foundPlayer: PlayerModel? = players.find { p -> p.id == id }
        return foundPlayer
    }

    override fun create(player: PlayerModel) {
        val key = db.child("users").child(userId).child("players").push().key
        key?.let {
            player.fbpId = key
            players.add(player)
            db.child("users").child(userId).child("players").child(key).setValue(player)
        }
    }

    override fun update(player: PlayerModel) {
        var foundPlayer: PlayerModel? = players.find { p -> p.fbpId == player.fbpId }
        if (foundPlayer != null) {
            foundPlayer.name = player.name
            foundPlayer.number = player.number
            foundPlayer.image = player.image
            foundPlayer.point = player.point
            foundPlayer.goal = player.goal
            foundPlayer.wide = player.wide
            foundPlayer.possession = player.possession
            foundPlayer.pass = player.pass

        }

        db.child("users").child(userId).child("players").child(player.fbpId).setValue(player)

    }

    override fun delete(player: PlayerModel) {
        db.child("users").child(userId).child("players").child(player.fbpId).removeValue()
        players.remove(player)
    }

    override fun clear() {
        players.clear()
    }

    fun fetchPlayers(playersReady: () -> Unit) {
        val valueEventListener = object : ValueEventListener {
            override fun onCancelled(dataSnapshot: DatabaseError) {
            }
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot!!.children.mapNotNullTo(players) { it.getValue<PlayerModel>(PlayerModel::class.java) }
                playersReady()
            }
        }
        userId = FirebaseAuth.getInstance().currentUser!!.uid
        db = FirebaseDatabase.getInstance().reference
        players.clear()
        db.child("users").child(userId).child("players").addListenerForSingleValueEvent(valueEventListener)
    }
}