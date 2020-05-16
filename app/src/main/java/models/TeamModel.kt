package models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class TeamModel (
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var fbId : String = "",
    var name: String = ""
) : Parcelable