package rma.lv1.mvvm.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import rma.lv1.mvvm.model.StepCounterModel

class StepCounterViewModel(context: Context) : ViewModel() {

    private val stepCounterModel = StepCounterModel(context)
    private val _steps = MutableLiveData(0)
    val steps: LiveData<Int> get() = _steps

    init {
        stepCounterModel.registerSensorListener()
    }

    fun updateSteps() {
        _steps.value = stepCounterModel.getSteps()
    }

    fun resetSteps() {
        stepCounterModel.resetSteps()
        _steps.value = 0
    }

    fun saveStepsData(onSuccess: (String) -> Unit, onFailure: (Exception) -> Unit) {
        stepCounterModel.saveStepsData(onSuccess, onFailure)
    }

    public override fun onCleared() {
        super.onCleared()
        stepCounterModel.unregisterSensorListener()
    }
}
