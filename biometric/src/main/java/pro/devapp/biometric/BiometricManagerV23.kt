package pro.devapp.biometric

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.core.hardware.fingerprint.FingerprintManagerCompat
import androidx.core.os.CancellationSignal
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

@TargetApi(Build.VERSION_CODES.M)
open class BiometricManagerV23(
    protected val context: Context,
    protected val title: String,
    protected val subtitle: String,
    protected val description: String,
    protected val negativeButtonText: String,
    protected val errorText: String,
    private val dialogV23: BiometricDialogV23Interface
) {
    protected var mCancellationSignalV23: CancellationSignal = CancellationSignal()

    fun displayBiometricPromptV23(biometricCallback: BiometricCallback) {
        val keyStore = generateKey()
        keyStore?.run {
            initCipher(this)?.let {
                val cryptoObject = FingerprintManagerCompat.CryptoObject(it)
                val fingerprintManagerCompat = FingerprintManagerCompat.from(context)
                fingerprintManagerCompat.authenticate(
                    cryptoObject, 0, mCancellationSignalV23,
                    object : FingerprintManagerCompat.AuthenticationCallback() {
                        override fun onAuthenticationError(errMsgId: Int, errString: CharSequence) {
                            super.onAuthenticationError(errMsgId, errString)
                            updateStatus(errString.toString())
                            biometricCallback.onAuthenticationError(errMsgId, errString)
                        }

                        override fun onAuthenticationHelp(
                            helpMsgId: Int,
                            helpString: CharSequence
                        ) {
                            super.onAuthenticationHelp(helpMsgId, helpString)
                            updateStatus(helpString.toString())
                            biometricCallback.onAuthenticationHelp(helpMsgId, helpString)
                        }

                        override fun onAuthenticationSucceeded(result: FingerprintManagerCompat.AuthenticationResult) {
                            super.onAuthenticationSucceeded(result)
                            dismissDialog()
                            biometricCallback.onAuthenticationSuccessful()
                        }

                        override fun onAuthenticationFailed() {
                            super.onAuthenticationFailed()
                            updateStatus(errorText)
                            biometricCallback.onAuthenticationFailed()
                        }
                    }, null
                )
                displayBiometricDialog()
            }
        }
    }

    private fun displayBiometricDialog() {
        dialogV23.apply {
            setTitle(title)
            setSubtitle(subtitle)
            setDescription(description)
            setButtonText(negativeButtonText)
            show()
        }
    }

    private fun dismissDialog() {
        dialogV23.dismiss()
    }

    private fun updateStatus(status: String) {
        dialogV23.updateStatus(status)
    }

    private fun generateKey(): KeyStore? {
        return try {
            val keyStore = KeyStore.getInstance("AndroidKeyStore")
            keyStore.load(null)
            val keyGenerator =
                KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
            keyGenerator.init(
                KeyGenParameterSpec.Builder(
                    KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                )
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build()
            )
            keyGenerator.generateKey()
            keyStore
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun initCipher(keyStore: KeyStore): Cipher? {
        return try {
            val cipher = Cipher.getInstance(
                KeyProperties.KEY_ALGORITHM_AES.toString() + "/"
                        + KeyProperties.BLOCK_MODE_CBC + "/"
                        + KeyProperties.ENCRYPTION_PADDING_PKCS7
            )
            keyStore.load(null)
            val key: SecretKey = keyStore.getKey(
                KEY_NAME,
                null
            ) as SecretKey
            cipher.init(Cipher.ENCRYPT_MODE, key)
            cipher
        } catch (e: Exception) {
            null
        }
    }

    companion object {
        private val KEY_NAME: String = java.util.UUID.randomUUID().toString()
    }
}