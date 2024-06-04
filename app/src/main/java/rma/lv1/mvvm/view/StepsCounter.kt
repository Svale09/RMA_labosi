package rma.lv1.mvvm.view

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import rma.lv1.mvvm.viewmodel.StepCounterViewModel
import rma.lv1.mvvm.viewmodel.StepCounterViewModelFactory

@Composable
fun StepCounter(navController: NavController) {
    val context = LocalContext.current
    val viewModel: StepCounterViewModel = viewModel(factory = StepCounterViewModelFactory(context))
    val steps by viewModel.steps.observeAsState(0)

    DisposableEffect(Unit) {
        viewModel.updateSteps()
        onDispose {
            viewModel.onCleared()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
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
                    viewModel.saveStepsData(
                        onSuccess = { documentId ->
                            Log.d("Firestore", "Steps data added with ID: $documentId")
                        },
                        onFailure = { e ->
                            Log.w("Firestore", "Error adding steps data", e)
                        }
                    )
                    viewModel.resetSteps()
                }
            ) {
                Text(text = "Stop walk")
            }
        }
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
