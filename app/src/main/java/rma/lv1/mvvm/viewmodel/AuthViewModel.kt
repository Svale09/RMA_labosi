package rma.lv1.mvvm.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import rma.lv1.mvvm.model.AuthModel

class AuthViewModel(context: Context) : ViewModel() {
    private val authModel: AuthModel = AuthModel(context)
    private val _authResult = MutableLiveData<Boolean>()
    val authResult: LiveData<Boolean> get() = _authResult

    fun signIn(email: String, password: String) {
        authModel.signIn(email, password) { result ->
            _authResult.value = result
        }
    }

    fun register(email: String, password: String) {
        authModel.register(email, password) { result ->
            _authResult.value = result
        }
    }
}
