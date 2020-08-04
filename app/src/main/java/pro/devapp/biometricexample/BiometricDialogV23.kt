package pro.devapp.biometricexample

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialog
import pro.devapp.biometric.BiometricCallback
import pro.devapp.biometric.BiometricDialogV23Interface

class BiometricDialogV23(
    context: Context,
    biometricCallback: BiometricCallback
) : BottomSheetDialog(context, R.style.BottomSheetDialogTheme), BiometricDialogV23Interface {
    private var btnCancel: Button? = null
    private var imgLogo: ImageView? = null
    private var itemTitle: TextView? = null
    private var itemDescription: TextView? = null
    private var itemSubtitle: TextView? = null
    private var itemStatus: TextView? = null
    private var biometricCallback: BiometricCallback? = biometricCallback

    init {
        setDialogView()
    }

    private fun setDialogView() {
        val bottomSheetView: View = layoutInflater.inflate(R.layout.dialog_biometric, null)
        setContentView(bottomSheetView)
        btnCancel = findViewById(R.id.btn_cancel)
        btnCancel?.setOnClickListener {
            dismiss()
            biometricCallback?.onAuthenticationCancelled()
        }
        imgLogo = findViewById(R.id.img_logo)
        itemTitle = findViewById(R.id.item_title)
        itemStatus = findViewById(R.id.item_status)
        itemSubtitle = findViewById(R.id.item_subtitle)
        itemDescription = findViewById(R.id.item_description)
        updateLogo()
    }

    override fun setTitle(title: String?) {
        itemTitle?.text = title
    }

    override fun updateStatus(status: String?) {
        itemStatus?.text = status
    }

    override fun setSubtitle(subtitle: String?) {
        itemSubtitle?.text = subtitle
    }

    override fun setDescription(description: String?) {
        itemDescription?.text = description
    }

    override fun setButtonText(negativeButtonText: String?) {
        btnCancel?.text = negativeButtonText
    }

    private fun updateLogo() {
        try {
            val drawable: Drawable = context.packageManager.getApplicationIcon(context.packageName)
            imgLogo?.setImageDrawable(drawable)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}