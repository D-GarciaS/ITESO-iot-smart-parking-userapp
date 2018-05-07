package mx.iteso.iot.androidappiot

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import mx.iteso.iot.androidappiot.retrofit.clients.BackEndService
import mx.iteso.iot.androidappiot.retrofit.clients.IoTAPI.UserAPI
import mx.iteso.iot.androidappiot.retrofit.models.SlotRequest
import mx.iteso.iot.androidappiot.retrofit.models.SuccessfulSlotResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TestAPI : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_api)
    }

    fun doAction(view: View){
        val clientApi = BackEndService().client.create(UserAPI::class.java)
        val req : SlotRequest = SlotRequest("asdad", "driver", listOf("a","b","c","d"), false );
        val call = clientApi.getSlot(req);

        Log.d("REQUEST", call.toString())

        call.enqueue( object:Callback<SuccessfulSlotResponse>{
            override fun onFailure(call: Call<SuccessfulSlotResponse>?, t: Throwable?) {
                Toast.makeText(applicationContext, "REQUEST", Toast.LENGTH_LONG).show()
                if (t != null) {
                    t.printStackTrace()
                };
            }

            override fun onResponse(call: Call<SuccessfulSlotResponse>?, response: Response<SuccessfulSlotResponse>?) {
                if(response != null && response.isSuccessful){
                    Log.d("OKCODE", response.body().toString())
                }else{
                    Log.d("ERRORCODE", response!!.code().toString())
                }
            }
        })

    }
}
