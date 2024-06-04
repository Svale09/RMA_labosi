package rma.lv1.mvvm.model

import android.content.Context
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class AuthModel(private val context: Context) {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun signIn(email: String, password: String, onResult: (Boolean) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Logged in successfully", Toast.LENGTH_SHORT).show()
                    onResult(true)
                } else {
                    Toast.makeText(context, "Login failed", Toast.LENGTH_SHORT).show()
                    onResult(false)
                }
            }
    }

    fun register(email: String, password: String, onResult: (Boolean) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Registered successfully", Toast.LENGTH_SHORT).show()
                    onResult(true)
                } else {
                    Toast.makeText(context, "Registration failed", Toast.LENGTH_SHORT).show()
                    onResult(false)
                }
            }
    }
}
