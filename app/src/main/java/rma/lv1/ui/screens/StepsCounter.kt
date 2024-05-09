package rma.lv1.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.math.sqrt

@Composable
fun StepCounter(navController: NavController) {
    var steps by rememberSaveable {
        mutableStateOf(0)
    }

    val stepsData = mapOf("steps" to steps)

    var previousX = 0f
    var previousY = 0f
    var previousZ = 0f

    val threshold = 15f

    val sensorManager =
        (LocalContext.current.getSystemService(Context.SENSOR_SERVICE) as SensorManager)

    val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    val sensorEventListener = object : SensorEventListener {
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

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

        }
    }

    // Register the sensor event listener
    DisposableEffect(Unit) {
        sensorManager.registerListener(
            sensorEventListener,
            sensor,
            SensorManager.SENSOR_DELAY_NORMAL
        )

        // Unregister the listener when the composable is disposed
        onDispose {
            sensorManager.unregisterListener(sensorEventListener)
        }
    }



    Box(modifier = Modifier.fillMaxSize()) {
        val db = Firebase.firestore


        BackgroundImage(modifier = Modifier.fillMaxSize())
        Column(modifier = Modifier.align(Alignment.Center)) {
            Text(
                text = "Step Count:",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(text = "$steps", fontSize = 80.sp)
            Button(
                onClick = {
                    db.collection("rma_lv")
                        .add(stepsData)
                        .addOnSuccessListener { documentReference ->
                            Log.d("Firestore", "Steps data added with ID: ${documentReference.id}")
                        }
                        .addOnFailureListener { e ->
                            Log.w("Firestore", "Error adding steps data", e)
                        }
                    steps = 0
                }
            ) {
                Text(text = "Stop walk")
            }
        }
        // Back button
        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            Text("BMI Screen")
        }
    }
}