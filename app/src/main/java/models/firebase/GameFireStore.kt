package models.firebase

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import models.GameModel
import models.GameStore
import org.jetbrains.anko.AnkoLogger

class GameFireStore(val context: Context) : GameStore, AnkoLogger {

    val games = ArrayList<GameModel>()
    lateinit var userId: String
    lateinit var db: DatabaseReference

    override fun findAll(): List<GameModel> {
        return games
    }

    override fun findById(id: Long): GameModel? {
        val foundGame: GameModel? = games.find { g -> g.id == id }
        return foundGame
    }

    override fun create(game: GameModel) : Long {
        val key = db.child("users").child(userId).child("games").push().key
        //key?.let {
            game.fbId = key!!
            game.id = key.hashCode().toLong()
            games.add(game)
            db.child("users").child(userId).child("games").child(key).setValue(game)
            return game.id
        //}
    }

    override fun update(game: GameModel) {
        var foundGame: GameModel? = games.find { g -> g.fbId == game.fbId }
        if (foundGame != null) {
            foundGame.title = game.title
            foundGame.score = game.score
            foundGame.win = game.win
        }

        db.child("users").child(userId).child("games").child(game.fbId).setValue(game)

    }

    override fun delete(game: GameModel) {
        db.child("users").child(userId).child("games").child(game.fbId).removeValue()
        games.remove(game)
    }

    override fun clear() {
        games.clear()
    }

    fun fetchGames(gamesReady: () -> Unit) {
        val valueEventListener = object : ValueEventListener {
            override fun onCancelled(dataSnapshot: DatabaseError) {
            }
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot!!.children.mapNotNullTo(games) { it.getValue<GameModel>(GameModel::class.java) }
                gamesReady()
            }
        }
        userId = FirebaseAuth.getInstance().currentUser!!.uid
        db = FirebaseDatabase.getInstance().reference
        games.clear()
        db.child("users").child(userId).child("games").addListenerForSingleValueEvent(valueEventListener)
    }
}