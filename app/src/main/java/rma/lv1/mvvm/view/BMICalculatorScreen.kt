package rma.lv1.mvvm.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import rma.lv1.mvvm.viewmodel.AuthViewModel
import rma.lv1.mvvm.viewmodel.AuthViewModelFactory
import rma.lv1.mvvm.viewmodel.BMIViewModel

@Composable
fun BMICalculatorScreen(viewModel: BMIViewModel, navController: NavController) {
    val context = LocalContext.current
    val authViewModel: AuthViewModel = viewModel(factory = AuthViewModelFactory(context))
    val authResult by authViewModel.authResult.observeAsState()

    var weight by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var bmiResult by remember { mutableStateOf<Float?>(null) }

    LaunchedEffect(authResult) {
        if (authResult == false) {
            navController.navigate("login_register") {
                popUpTo("bmi_screen") { inclusive = true }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        BackgroundImage(modifier = Modifier)
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = weight,
                onValueChange = { weight = it },
                label = { Text("Weight (kg)") },
                keyboardOptions = KeyboardOptions(
                    keyboardType =
                    KeyboardType.Number
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = height,
                onValueChange = { height = it },
                label = { Text("Height (cm)") },
                keyboardOptions = KeyboardOptions(
                    keyboardType =
                    KeyboardType.Number
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    // Izracun preko viewmodela
                    bmiResult = viewModel.calculateBMI(
                        weight.toFloatOrNull(),
                        height.toFloatOrNull()
                    )
                }
            ) {
                Text("Calculate BMI")
            }
            Spacer(modifier = Modifier.height(16.dp))
            // Display BMI result
            Text(
                text = "Your BMI: ${bmiResult?.toString() ?: "N/A"}",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Button(
                modifier = Modifier
                    .padding(16.dp)
                    .width(150.dp)
                    .height(50.dp),
                onClick = { navController.navigate("step_counter") },
            ) {
                Text(text = "Steps counter")
            }
            Button(
                onClick = { authViewModel.logout() },
                modifier = Modifier
                    .padding(16.dp)
                    .width(150.dp)
                    .height(50.dp),
            ) {
                Text("Logout")
            }
        }
    }
}