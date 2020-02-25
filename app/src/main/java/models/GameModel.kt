package models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GameModel(
    var id: Long = 0,
    var title: String = "",
    var score: String = "") : Parcelable