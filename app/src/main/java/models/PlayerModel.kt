package models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PlayerModel(
    var id: Long = 0,
    var name: String = "",
    var number: String = "",
    var image: String = "",
    var point: Int = 0,
    var goal: Int = 0,
    var wide: Int = 0,
    var possession: Int = 0,
    var pass: Int = 0,
    var accuracy: Double = 0.00) : Parcelable