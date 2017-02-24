package com.drassapps.misscode;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

@TargetApi(23)
public class FingerprintHandler extends
        FingerprintManager.AuthenticationCallback{

    private CancellationSignal cancellationSignal;
    private Context appContext;

    public FingerprintHandler(Context context) {
        appContext = context;
    }

    public void startAuth(FingerprintManager manager,
                          FingerprintManager.CryptoObject cryptoObject) {

        cancellationSignal = new CancellationSignal();

        if (ActivityCompat.checkSelfPermission(appContext,
                Manifest.permission.USE_FINGERPRINT) !=
                PackageManager.PERMISSION_GRANTED) {
            return;
        }
        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }

    @Override
    public void onAuthenticationError(int errMsgId,
                                      CharSequence errString) {
        Toast.makeText(appContext,
                errString,
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationHelp(int helpMsgId,
                                     CharSequence helpString) {
        Toast.makeText(appContext,
                helpString,
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationFailed() {


        // Get instance of Vibrator from current Context
        Vibrator v = (Vibrator) appContext.getSystemService(Context.VIBRATOR_SERVICE);

        // Vibrate for 300 milliseconds
        v.vibrate(300);

        Toast.makeText(appContext,
                "Error, vuelve a intentarlo",
                Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onAuthenticationSucceeded(
            FingerprintManager.AuthenticationResult result) {

        // todo hacer aqui ir a la nueva ventana
        Intent i1 = new Intent (appContext, HomeScreen.class);
        appContext.startActivity(i1);

        Toast.makeText(appContext,
                "Bienvenido",
                Toast.LENGTH_SHORT).show();
    }

}
