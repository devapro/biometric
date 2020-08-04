# Biometric-Auth
Add Biometric Authentication to any Android app</br>

This library based on https://github.com/anitaa1990/Biometric-Auth-Sample. It is kotlin version with some improvements.


This library provides an easy way to implement fingerprint authentication without having to deal with all the boilerplate stuff going on inside.


<img src="https://img.shields.io/badge/API-23%2B-blue.svg?style=flat" style="max-width:100%;" alt="API" data-canonical-src="https://img.shields.io/badge/API-23%2B-blue.svg?style=flat" style="max-width:100%;">


<p>
<a href="https://github.com/devapro/biometric/blob/master/media/Screenshot.png" target="_blank">
<img src="https://github.com/devapro/biometric/blob/master/media/Screenshot.png" width="250" style="max-width:100%;">
</a></p>

<h2>How to integrate the library in your app?</h2>
<b>Gradle Dependecy</b></br>

```gradle
dependencies {
        implementation 'pro.devapp.biometric:biometric:1.0.0'
}
```

<h2>Usage</h2>

```
val dialogV23: BiometricDialogV23Interface = ....
        BiometricManager(
            this@MainActivity,
            "Title",
            "Subtitle example",
            "Description example",
            "Cancel",
            "Error text",
            dialogV23
        ).authenticate(biometricCallback)
```
The ```BiometricDialogV23Interface``` interface for dialog to support devices before BiometricPrompt. You can found implementation in example app

The ```BiometricCallback``` class has the following callback methods:

```
BiometricCallback {
              override fun onSdkVersionNotSupported() {
                     /*  
                      *  Will be called if the device sdk version does not support Biometric authentication
                      */
               }

               override fun onBiometricAuthenticationNotSupported() {
                     /*  
                      *  Will be called if the device does not contain any fingerprint sensors 
                      */
               }

               override fun  onBiometricAuthenticationNotAvailable() {
                    /*  
                     *  The device does not have any biometrics registered in the device.
                     */
               }

               override fun onBiometricAuthenticationPermissionNotGranted() {
                      /*  
                       *  android.permission.USE_BIOMETRIC permission is not granted to the app
                       */
               }

                override fun onBiometricAuthenticationInternalError(String error) {
                     /*  
                      *  This method is called if one of the fields such as the title, subtitle, 
                      * description or the negative button text is empty
                      */
               }

               override fun  onAuthenticationFailed() {
                      /*  
                       * When the fingerprint doesn’t match with any of the fingerprints registered on the device, 
                       * then this callback will be triggered.
                       */
               }

               override fun  onAuthenticationCancelled() {
                       /*  
                        * The authentication is cancelled by the user. 
                        */
               }

               override fun  onAuthenticationSuccessful() {
                        /*  
                         * When the fingerprint is has been successfully matched with one of the fingerprints   
                         * registered on the device, then this callback will be triggered. 
                         */
               }

               override fun  onAuthenticationHelp(int helpCode, CharSequence helpString) {
                         /*  
                          * This method is called when a non-fatal error has occurred during the authentication 
                          * process. The callback will be provided with an help code to identify the cause of the 
                          * error, along with a help message.
                          */
                }

               override fun  onAuthenticationError(int errorCode, CharSequence errString) {
                         /*  
                          * When an unrecoverable error has been encountered and the authentication process has 
                          * completed without success, then this callback will be triggered. The callback is provided 
                          * with an error code to identify the cause of the error, along with the error message. 
                          */
                 }
              })

```

