package rma.lv1.mvvm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import rma.lv1.mvvm.ui.theme.LV1Theme
import rma.lv1.mvvm.view.BMICalculatorScreen
import rma.lv1.mvvm.view.LoginRegisterScreen
import rma.lv1.mvvm.view.StepCounter
import rma.lv1.mvvm.viewmodel.BMIViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LV1Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val currentUser = FirebaseAuth.getInstance().currentUser

                    NavHost(
                        navController = navController,
                        startDestination = if (currentUser == null) "login_register" else "bmi_screen"
                    ) {
                        composable("login_register") {
                            LoginRegisterScreen(navController = navController)
                        }
                        composable("bmi_screen") {
                            BMICalculatorScreen(viewModel = BMIViewModel(), navController)
                        }
                        composable("step_counter") {
                            StepCounter(navController = navController)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
fun BMIScreen() {
    LV1Theme {
        // Preview function content
    }
}

