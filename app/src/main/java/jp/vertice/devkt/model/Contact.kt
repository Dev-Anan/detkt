package jp.vertice.devkt.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Contact(@PrimaryKey val uid: Long = Date().time) : Parcelable {
    constructor(title: String = "", description: String = "") : this() {
        this.title = title
        this.description = description
    }

    @ColumnInfo(name = "name_kanzi")
    var title: String = ""

    @ColumnInfo(name = "no")
    var no: Long? = null

    @ColumnInfo(name = "memo")
    var description: String = ""

    @ColumnInfo(name = "tel_no")
    var numberPhone: String = ""


    @ColumnInfo(name = "category_kbn")
    var categoryKbn: Long? = null

    @ColumnInfo(name = "kbn1_text")
    var categoryName: String = ""

    constructor(parcel: Parcel) : this(parcel.readLong()) {
        title = parcel.readString()!!
        no = parcel.readValue(Long::class.java.classLoader) as? Long
        description = parcel.readString()!!
        numberPhone = parcel.readString()!!
        categoryKbn = parcel.readValue(Long::class.java.classLoader) as? Long
        categoryName = parcel.readString()!!
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(uid)
        parcel.writeString(title)
        parcel.writeValue(no)
        parcel.writeString(description)
        parcel.writeString(numberPhone)
        parcel.writeValue(categoryKbn)
        parcel.writeString(categoryName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Contact> {
        override fun createFromParcel(parcel: Parcel): Contact {
            return Contact(parcel)
        }

        override fun newArray(size: Int): Array<Contact?> {
            return arrayOfNulls(size)
        }
    }


}
