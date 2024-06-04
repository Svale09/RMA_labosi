package rma.lv1.mvvm.viewmodel

import androidx.lifecycle.ViewModel
import rma.lv1.mvvm.model.BMIModel
import kotlin.text.Typography.less

class BMIViewModel : ViewModel() {
    val bmiModel = BMIModel(weight = null, height = null, bmiResult = null)
    fun calculateBMI(weight: Float?, height: Float?): Float? {
        return if (weight != null && height != null && height > 0) {
            val bmi = weight / ((height / 100) * (height / 100))
            bmiModel.bmiResult = bmi
            bmi // Return calculated BMI
        } else {
            bmiModel.bmiResult = null
            null // Return null if weight or height is null or height is less than or equal to 0
        }
    }
}

