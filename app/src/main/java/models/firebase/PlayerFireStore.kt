package models.firebase

import android.content.Context
import android.graphics.Bitmap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import helpers.readImageFromPath
import models.GameModel
import models.PlayerModel
import models.PlayerStore
import org.jetbrains.anko.AnkoLogger
import java.io.ByteArrayOutputStream
import java.io.File

class PlayerFireStore(val context: Context) : PlayerStore, AnkoLogger {

    val players = ArrayList<PlayerModel>()
    val game = GameModel()

    lateinit var userId: String

    lateinit var db: DatabaseReference
    lateinit var st: StorageReference

    override fun findAll(): List<PlayerModel> {
        return players
    }

    override fun findById(id: Long): PlayerModel? {
        val foundPlayer: PlayerModel? = players.find { p -> p.id == id }
        return foundPlayer
    }

    override fun create(player: PlayerModel) : Long {
        val key = db.child("users").child(userId).child("players").push().key
        //key?.let {
            player.fbId = key!!
            player.id = key.hashCode().toLong()
            players.add(player)
            db.child("users").child(userId).child("players").child(key).setValue(player)
            updateImage(player)
            return player.id
        //}
    }

    override fun update(player: PlayerModel) {
        var foundPlayer: PlayerModel? = players.find { p -> p.fbId == player.fbId }
        if (foundPlayer != null) {
            foundPlayer.name = player.name
            foundPlayer.number = player.number
            foundPlayer.image = player.image
            foundPlayer.point = player.point
            foundPlayer.goal = player.goal
            foundPlayer.wide = player.wide
            foundPlayer.possession = player.possession
            foundPlayer.lostPossession = player.lostPossession
            foundPlayer.pass = player.pass
            foundPlayer.missedPass = player.missedPass
            foundPlayer.gameId = player.gameId
            foundPlayer.accuracy = player.accuracy
            foundPlayer.passingAcc = player.passingAcc
            foundPlayer.ballRetention = player.ballRetention
        }

        db.child("users").child(userId).child("players").child(player.fbId).setValue(player)
        if ((player.image.length) > 0 && (player.image[0] != 'h')) {
            updateImage(player)
        }

    }

    override fun delete(player: PlayerModel) {
        db.child("users").child(userId).child("players").child(player.fbId).removeValue()
        players.remove(player)
    }


    override fun clear() {
        players.clear()
    }

    fun updateImage(player: PlayerModel) {
        if (player.image != "") {
            val fileName = File(player.image)
            val imageName = fileName.getName()

            var imageRef = st.child(userId + '/' + imageName)
            val baos = ByteArrayOutputStream()
            val bitmap = readImageFromPath(context, player.image)

            bitmap?.let {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val data = baos.toByteArray()
                val uploadTask = imageRef.putBytes(data)
                uploadTask.addOnFailureListener {
                    println(it.message)
                }.addOnSuccessListener { taskSnapshot ->
                    taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener {
                        player.image = it.toString()
                        db.child("users").child(userId).child("players").child(player.fbId).setValue(player)
                    }
                }
            }
        }
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
        st = FirebaseStorage.getInstance().reference
        players.clear()
        db.child("users").child(userId).child("players").addListenerForSingleValueEvent(valueEventListener)
    }


}