package mx.iteso.iot.androidappiot.retrofit.clients

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BackEndService {
    private val baseURL = "https://iot-service.azurewebsites.net/"

    val client = Retrofit.Builder()
        .baseUrl(baseURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}
