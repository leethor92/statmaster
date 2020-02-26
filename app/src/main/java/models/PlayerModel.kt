package models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PlayerModel(
    var id: Long = 0,
    var name: String = "",
    var number: String = "",
    var image: String = "") : Parcelable