package rma.lv1.mvvm.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class StepCounterViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StepCounterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StepCounterViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
