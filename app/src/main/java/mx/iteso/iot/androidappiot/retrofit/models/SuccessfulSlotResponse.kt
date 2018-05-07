package mx.iteso.iot.androidappiot.retrofit.models

import android.os.Parcel
import android.os.Parcelable

data class SuccessfulSlotResponse (
    val section: String,
    val number: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(section)
        parcel.writeString(number)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SuccessfulSlotResponse> {
        override fun createFromParcel(parcel: Parcel): SuccessfulSlotResponse {
            return SuccessfulSlotResponse(parcel)
        }

        override fun newArray(size: Int): Array<SuccessfulSlotResponse?> {
            return arrayOfNulls(size)
        }
    }
}