package mx.iteso.iot.androidappiot.retrofit.clients.IoTAPI

import mx.iteso.iot.androidappiot.retrofit.models.AuthResponse
import retrofit2.Call
import retrofit2.http.*

interface AuthenticationAPI {
    @POST("/auth")
    fun auth(@Header("Authorization") credentails:String) : Call<AuthResponse>
}