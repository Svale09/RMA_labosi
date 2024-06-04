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
import rma.lv1.mvvm.ui.screens.BackgroundImage
import rma.lv1.mvvm.ui.theme.LV1Theme
import rma.lv1.mvvm.ui.screens.BMIScreen
import rma.lv1.mvvm.ui.screens.StepCounter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "bmi_screen") {
                composable("bmi_screen") {
                    BMIScreen(name = "Ivan Svalina", navController = navController)
                }
                composable("step_counter") {
                    StepCounter(navController = navController)
                }
        }

            LV1Theme {
                // A surface container using the 'background' color from the theme

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background

                ) {
                    NavHost(navController = navController, startDestination = "bmi_screen") {
                        composable("bmi_screen") {
                            BMIScreen(name = "Ivan Svalina", navController = navController)
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
        BackgroundImage(modifier = Modifier)   }
}