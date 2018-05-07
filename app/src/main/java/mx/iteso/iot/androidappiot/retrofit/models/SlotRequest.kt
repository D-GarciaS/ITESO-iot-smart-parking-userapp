package mx.iteso.iot.androidappiot.retrofit.models

import android.os.Parcel
import android.os.Parcelable

data class SlotRequest(
    val plates: String,
    val driver: String,
    val Passenger: List<String>,
    val retry: Boolean
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.createStringArrayList(),
            parcel.readByte() != 0.toByte()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(plates)
        parcel.writeString(driver)
        parcel.writeStringList(Passenger)
        parcel.writeByte(if (retry) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SlotRequest> {
        override fun createFromParcel(parcel: Parcel): SlotRequest {
            return SlotRequest(parcel)
        }

        override fun newArray(size: Int): Array<SlotRequest?> {
            return arrayOfNulls(size)
        }
    }
}
