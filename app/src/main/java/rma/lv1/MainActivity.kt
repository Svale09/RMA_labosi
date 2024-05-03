package rma.lv1

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import rma.lv1.ui.theme.LV1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LV1Theme {
                // A surface container using the 'background' color from the theme

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background

                ) {
                    BackgroundImage(modifier = Modifier)
                }
            }
        }
    }
}



@Composable
fun UserPreview(name: String, visina: Float, tezina: Float, modifier: Modifier = Modifier) {
    

    var bmi = tezina / (visina * visina);
    var formattedBMI by rememberSaveable { mutableStateOf("") }
    var weight by remember { mutableStateOf(0f)}
    var height by remember { mutableStateOf(0f)}

    val db = Firebase.firestore

    val user = hashMapOf(
        "height" to height,
        "weight" to weight
    )

    Text(
        text = "Pozdrav $name!",
        fontSize = 20.sp,
        lineHeight = 56.sp,
        modifier= Modifier
            .padding(top = 8.dp)
            .padding(start = 10.dp)

    )

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = "Tvoj BMI je:",
            fontSize = 55.sp,
            lineHeight = 61.sp,
            textAlign = TextAlign.Center,


            )
        Text(
            text = formattedBMI,
            fontSize = 70.sp,
            lineHeight = 72.sp,
            fontWeight = FontWeight.Bold,
        )
        TextField(
            value = weight.toString(),
            onValueChange = { weight = it.toFloatOrNull() ?: 0f },
            label = { Text("Nova Tezina:") },
            modifier = Modifier.fillMaxWidth(0.8f)
        )
        TextField(
            value = height.toString(),
            onValueChange = { height = it.toFloatOrNull() ?: 0f },
            label = { Text("Nova visina:") },
            modifier = Modifier.fillMaxWidth(0.8f)
        )
        Button(
            onClick = {
                bmi = CalculateBMI(weight, height);
                formattedBMI = "%.2f".format(bmi)
                db.collection("rma_lv").document("BMI_lv2")
                    .update(user.toMap())
                    .addOnSuccessListener {
                        Log.d("Firestore", "DocumentSnapshot successfully updated!")
                    }
                    .addOnFailureListener { e ->
                        Log.w("Firestore", "Error updating document", e)
                    }
            },
                    content = {
                Text(text = "Izračunaj BMI")
            }
        )
    }
}

fun CalculateBMI(weight: Float, height: Float): Float{
    val bmi = weight / (height * height) * 10000;
    return bmi;
}
@Composable
fun BackgroundImage(modifier: Modifier) {

    Box (modifier){ Image(
        painter = painterResource(id = R.drawable.fitness),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        alpha = 0.1F
    )
        UserPreview(name = "Miljenko", visina = 1.91f, tezina =1000f, modifier = Modifier.fillMaxSize())
    }

}
@Preview(showBackground = false)
@Composable
fun UserPreview() {
    LV1Theme {
        BackgroundImage(modifier = Modifier)   }
}