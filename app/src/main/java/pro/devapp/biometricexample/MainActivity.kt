package pro.devapp.biometricexample

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import pro.devapp.biometric.BiometricCallback
import pro.devapp.biometric.BiometricManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.button).setOnClickListener {
            checkBiometric()
        }
    }

    private fun checkBiometric() {
        val biometricCallback = object : BiometricCallback {
            override fun onSdkVersionNotSupported() {
                Log.d("FP", "onSdkVersionNotSupported")
                Toast.makeText(this@MainActivity, "onSdkVersionNotSupported", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onBiometricAuthenticationNotSupported() {
                Log.d("FP", "onBiometricAuthenticationNotSupported")
                Toast.makeText(
                    this@MainActivity,
                    "onBiometricAuthenticationNotSupported",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onBiometricAuthenticationNotAvailable() {
                Log.d("FP", "onBiometricAuthenticationNotAvailable")
                Toast.makeText(
                    this@MainActivity,
                    "onBiometricAuthenticationNotAvailable",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onBiometricAuthenticationPermissionNotGranted() {
                Log.d("FP", "onBiometricAuthenticationPermissionNotGranted")
                Toast.makeText(
                    this@MainActivity,
                    "onBiometricAuthenticationPermissionNotGranted",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onBiometricAuthenticationInternalError(error: String) {
                Toast.makeText(this@MainActivity, error, Toast.LENGTH_SHORT).show()
            }

            override fun onAuthenticationFailed() {
                Log.d("FP", "onAuthenticationFailed")
                Toast.makeText(this@MainActivity, "onAuthenticationFailed", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onAuthenticationCancelled() {
                Log.d("FP", "onAuthenticationCancelled")
                Toast.makeText(this@MainActivity, "onAuthenticationCancelled", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onAuthenticationSuccessful() {
                Log.d("FP", "onAuthenticationSuccessful")
                Toast.makeText(this@MainActivity, "onAuthenticationSuccessful", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence) {
                Toast.makeText(this@MainActivity, helpString, Toast.LENGTH_SHORT).show()
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                Toast.makeText(this@MainActivity, errString, Toast.LENGTH_SHORT).show()
            }
        }
        val dialogV23 = BiometricDialogV23(this@MainActivity, biometricCallback)
        BiometricManager(
            this@MainActivity,
            "Title",
            "Subtitle example",
            "Description example",
            "Cancel",
            "Error text",
            dialogV23
        ).authenticate(biometricCallback)
    }
}