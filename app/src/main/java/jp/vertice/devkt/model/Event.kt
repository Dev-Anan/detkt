package jp.vertice.devkt.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Event(@PrimaryKey val uid: Long = Date().time) : Parcelable {

    constructor(title: String = "", description: String = "", imageName : String = "") : this() {
        this.title = title
        this.description = description
        this.imageName = imageName
    }

    @ColumnInfo(name = "ポイント名")
    var title : String = ""

    @ColumnInfo(name = "メッセージ")
    var description : String = ""

    @ColumnInfo(name = "連番")
    var renbun : Long? = null

    @ColumnInfo(name = "プッシュ区分")
    var readFlag : Long? = null

    @ColumnInfo(name = "画像パス")
    var imageName : String = ""


    constructor(parcel: Parcel) : this(parcel.readLong()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(uid)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Event> {
        override fun createFromParcel(parcel: Parcel): Event {
            return Event(parcel)
        }

        override fun newArray(size: Int): Array<Event?> {
            return arrayOfNulls(size)
        }
    }

}