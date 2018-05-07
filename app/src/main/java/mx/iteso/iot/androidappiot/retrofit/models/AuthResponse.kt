package mx.iteso.iot.androidappiot.retrofit.models

import android.os.Parcel
import android.os.Parcelable

data class AuthResponse(
		val token: String,
		val user: User
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            TODO("user")) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(token)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AuthResponse> {
        override fun createFromParcel(parcel: Parcel): AuthResponse {
            return AuthResponse(parcel)
        }

        override fun newArray(size: Int): Array<AuthResponse?> {
            return arrayOfNulls(size)
        }
    }
}