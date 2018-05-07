package mx.iteso.iot.androidappiot

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import mx.iteso.iot.androidappiot.retrofit.clients.BackEndService
import mx.iteso.iot.androidappiot.retrofit.clients.IoTAPI.UserAPI
import mx.iteso.iot.androidappiot.retrofit.models.ParkingFailResponse
import mx.iteso.iot.androidappiot.retrofit.models.SlotRequest
import mx.iteso.iot.androidappiot.retrofit.models.SuccessfulSlotResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class ActivityMain : AppCompatActivity() {

    private val credeciales = ArrayList<String>(Collections.nCopies(4, null));
    private val plates = "ABC-123"

    private var last: Int = 0;

    private lateinit var sendButton: Button
    private lateinit var conductorBtn: ImageButton
    private lateinit var p1Btn: ImageButton
    private lateinit var p2Btn: ImageButton
    private lateinit var p3Btn: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //IntentIntegrator(this).initiateScan(); // `this` is the current Activity

        sendButton = findViewById<Button>(R.id.ActivityMain_send_btn);
        conductorBtn = findViewById<ImageButton>(R.id.ActivityMain_conductor_btn);
        p1Btn = findViewById<ImageButton>(R.id.ActivityMain_pass1_btn);
        p2Btn = findViewById<ImageButton>(R.id.ActivityMain_pass2_btn);
        p3Btn = findViewById<ImageButton>(R.id.ActivityMain_pass3_btn);

        conductorBtn.setOnClickListener({ button -> scanCredential(0) });
        p1Btn.setOnClickListener({ button -> scanCredential(1) });
        p2Btn.setOnClickListener({ button -> scanCredential(2) });
        p3Btn.setOnClickListener({ button -> scanCredential(3) });

        sendButton.setOnClickListener({ button -> sendCredentials() })

    }

    private fun scanCredential(n: Int) {
        last = n;
        IntentIntegrator(this)
                .setPrompt("Escanea el codigo de barras")
                .setCaptureActivity(ActivityCustomScan::class.java)
                .setOrientationLocked(false)
                .initiateScan() // `this` is the current Activity

        //startActivity(intent)
    }

    private fun sendCredentials() {
        val concat: String = credeciales.reduce { acc, s -> acc.plus("," + s) }

        val pasajeros = credeciales.subList(1, 3).toList()
        val rideReques = SlotRequest(
                plates,
                credeciales[0],
                pasajeros,
                false)

        val clientApi = BackEndService().client.create(UserAPI::class.java)

        val call = clientApi.getSlot(rideReques);
        Log.d("REQUEST", call.toString())

        call.enqueue( object: Callback<SuccessfulSlotResponse> {
            override fun onFailure(call: Call<SuccessfulSlotResponse>?, t: Throwable?) {
                Toast.makeText(applicationContext, "Fallo", Toast.LENGTH_LONG).show()
                if (t != null) {
                    t.printStackTrace()
                };
            }

            override fun onResponse(call: Call<SuccessfulSlotResponse>?, response: Response<SuccessfulSlotResponse>?) {
                Toast.makeText(applicationContext, "Exito" + response.toString(), Toast.LENGTH_LONG).show()
                if(response != null && response.isSuccessful){
                    val succesfulResponse = response.body()
                    successfulRequest(rideReques, succesfulResponse!!);
                }else{
                    val failedRequestResponse = ParkingFailResponse(1, "Failed", null)
                    failedRequest(rideReques, failedRequestResponse);
                }
            }
        })
    }

    private fun failedRequest(failedRequestResponse: SlotRequest, failedRequestResponse1: ParkingFailResponse) {
        val rand: Int = (Math.random() * 3).toInt()
        colorButton(rand, false)
    }

    private fun successfulRequest(originalRequest: SlotRequest, succesfulResponse: SuccessfulSlotResponse) {
        val intent: Intent = Intent(this, ActivitySuccess::class.java).apply {
            putExtra("Request", originalRequest)
            putExtra("Response", succesfulResponse)
        }
        startActivity(intent)
    }

    // Get the results:
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                credeciales[last] = result.contents
                colorButton(last, true)
                Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun colorButton(last: Int, valid: Boolean) {
        val color = if (valid) Color.GREEN else Color.RED
        if (last == 0) {
            conductorBtn.setBackgroundColor(color)
        } else if (last == 1) {
            p1Btn.setBackgroundColor(color)
        } else if (last == 2) {
            p2Btn.setBackgroundColor(color)
        } else if (last == 3) {
            p3Btn.setBackgroundColor(color)
        }
    }
}
