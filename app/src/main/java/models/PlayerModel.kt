package models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import models.GameModel

@Parcelize
@Entity(foreignKeys = [ForeignKey(entity = GameModel::class, parentColumns = ["id"], childColumns = ["gameId"]),
    ForeignKey(entity = TeamModel::class, parentColumns = ["id"], childColumns = ["teamId"])]
)
data class PlayerModel(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var fbId : String = "",
    var name: String = "",
    var number: String = "",
    var image: String = "",
    var point: Int = 0,
    var goal: Int = 0,
    var wide: Int = 0,
    var gameId: Long = 0,
    var teamId: Long = 0,
    var possession: Int = 0,
    var lostPossession: Int = 0,
    var pass: Int = 0,
    var missedPass: Int = 0,
    var accuracy: Double = 0.00,
    var passingAcc: Double = 0.00,
    var ballRetention: Double = 0.00) : Parcelable