package rma.lv1.mvvm.model

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.google.firebase.firestore.FirebaseFirestore

class StepCounterModel(context: Context) {

    private val sensorManager: SensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val sensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    private val db = FirebaseFirestore.getInstance()

    private var steps = 0
    private var previousX = 0f
    private var previousY = 0f
    private var previousZ = 0f
    private val threshold = 15f

    private val sensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
                val deltaX = event.values[0] - previousX
                val deltaY = event.values[1] - previousY
                val deltaZ = event.values[2] - previousZ

                if (deltaX > threshold || deltaY > threshold || deltaZ > threshold) {
                    steps++
                }
                previousX = event.values[0]
                previousY = event.values[1]
                previousZ = event.values[2]
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
    }

    fun registerSensorListener() {
        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    fun unregisterSensorListener() {
        sensorManager.unregisterListener(sensorEventListener)
    }

    fun resetSteps() {
        steps = 0
    }

    fun getSteps() = steps

    fun saveStepsData(onSuccess: (String) -> Unit, onFailure: (Exception) -> Unit) {
        val stepsData = mapOf("steps" to steps)
        db.collection("rma_lv")
            .add(stepsData)
            .addOnSuccessListener { documentReference ->
                onSuccess(documentReference.id)
            }
            .addOnFailureListener { e ->
                onFailure(e)
            }
    }
}
