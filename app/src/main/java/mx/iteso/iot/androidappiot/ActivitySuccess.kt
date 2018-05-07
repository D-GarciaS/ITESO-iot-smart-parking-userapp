package mx.iteso.iot.androidappiot

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_success.*
import mx.iteso.iot.androidappiot.retrofit.models.ParkingFailResponse
import mx.iteso.iot.androidappiot.retrofit.models.SlotRequest
import mx.iteso.iot.androidappiot.retrofit.models.SuccessfulSlotResponse

class ActivitySuccess : AppCompatActivity() {

    private lateinit var requestNew_Btn: Button
    private lateinit var assignedLot_Txv: TextView

    private lateinit var request: SlotRequest
    private lateinit var response: SuccessfulSlotResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success)
        setSupportActionBar(toolbar)

        getBundleData(savedInstanceState)
        bindAllViews()
        initAllViews()

        requestNew_Btn.setOnClickListener { it: View ->
            retryRequest()
        }
    }

    private fun initAllViews() {
        val message = "Tu lugar es " + response.section + response.number
        assignedLot_Txv.setText(message)
    }

    private fun getBundleData(savedInstanceState: Bundle?) {
        Log.e("Null error", intent.toString())

        response = intent.extras.getParcelable("Response")
    }

    private fun bindAllViews() {
        requestNew_Btn = findViewById(R.id.ActivitySuccess_RequestNew_Btn);
        assignedLot_Txv = findViewById(R.id.ActivitySuccess_AssignedLot)
    }

    private fun retryRequest() {
        val rand = Math.random()
        if (rand > 0.5) {
            val parkingSuccesfulResponse = SuccessfulSlotResponse("A", "0")
            succesfulRetry(parkingSuccesfulResponse)
        } else {
            val parkingFailResponse = ParkingFailResponse(1,
                    "Hubo un problema interno, lamentamos los inconvenientes." +
                            " Por favor busca un nuevo lugar en el estacionamiento general",
                    null)
            unsuccesfulRetry(parkingFailResponse)
        }
    }

    private fun succesfulRetry(parkingSuccesfulResponse: SuccessfulSlotResponse) {
        val message = "Tu nuevo lugar es " + response.section + response.number
        assignedLot_Txv.setText(message)
    }

    private fun unsuccesfulRetry(parkingFailResponse: ParkingFailResponse) {
        val t = Toast.makeText(applicationContext, parkingFailResponse.message, Toast.LENGTH_LONG)
        t.show()
        assignedLot_Txv.setText("Problema interno")
        assignedLot_Txv.setTextColor(Color.RED)
        requestNew_Btn.isClickable = false
    }

}
