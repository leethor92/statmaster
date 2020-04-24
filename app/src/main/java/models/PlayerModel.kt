package models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import models.GameModel

@Parcelize
@Entity
data class PlayerModel(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var name: String = "",
    var number: String = "",
    var image: String = "",
    var point: Int = 0,
    var goal: Int = 0,
    var wide: Int = 0,
    var possession: Int = 0,
    var pass: Int = 0,
    var accuracy: Double = 0.00) : Parcelable