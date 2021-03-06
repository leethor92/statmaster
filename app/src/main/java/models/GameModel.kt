package models

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(foreignKeys = [ForeignKey(entity = TeamModel::class, parentColumns = ["id"], childColumns = ["teamId"])])
data class GameModel(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var fbId : String = "",
    var title: String = "",
    var score: String = "",
    var goal: String = "",
    var point: String = "",
    var win: Boolean = false,
    var draw: Boolean = false,
    var loss: Boolean = false,
    var teamId: Long = 0,
    @Embedded var players: MutableList<PlayerModel> = ArrayList()
    ) : Parcelable