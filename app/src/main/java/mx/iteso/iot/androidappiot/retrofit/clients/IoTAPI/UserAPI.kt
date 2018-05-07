package mx.iteso.iot.androidappiot.retrofit.clients.IoTAPI

import mx.iteso.iot.androidappiot.retrofit.models.SlotRequest
import mx.iteso.iot.androidappiot.retrofit.models.SuccessfulSlotResponse
import retrofit2.Call
import retrofit2.http.*

interface UserAPI {
    @POST("/slotrequest")
    fun getSlot(@Body req: SlotRequest) : Call<SuccessfulSlotResponse>
}