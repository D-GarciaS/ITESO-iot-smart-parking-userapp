package mx.iteso.iot.androidappiot.retrofit.models

import android.os.Parcel
import android.os.Parcelable

data class ParkingFailResponse(
    val code: Int,
    val message:String,
    val invalidBarCodes:List<String>?
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.createStringArrayList()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(code)
        parcel.writeString(message)
        parcel.writeStringList(invalidBarCodes)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ParkingFailResponse> {
        override fun createFromParcel(parcel: Parcel): ParkingFailResponse {
            return ParkingFailResponse(parcel)
        }

        override fun newArray(size: Int): Array<ParkingFailResponse?> {
            return arrayOfNulls(size)
        }
    }
}