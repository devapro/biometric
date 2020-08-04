package pro.devapp.biometric

interface BiometricDialogV23Interface {
    fun show()
    fun dismiss()
    fun updateStatus(status: String?)
    fun setTitle(title: String?)
    fun setSubtitle(subtitle: String?)
    fun setDescription(description: String?)
    fun setButtonText(negativeButtonText: String?)
}