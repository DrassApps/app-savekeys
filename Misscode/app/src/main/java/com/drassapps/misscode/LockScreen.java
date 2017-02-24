package com.drassapps.misscode;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.os.CancellationSignal;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

@TargetApi(23)
public class LockScreen extends Activity{

    private TextView text_email, text_pass;
    private EditText pass;
    private Button bt_cacelar, bt_acceder;
    private ImageView huella;

    private android.os.CancellationSignal cancellationSignal;

    private static final String KEY_NAME = "key_123";
    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;
    private KeyStore keyStore;
    private KeyGenerator keyGenerator;
    private Cipher cipher;
    private FingerprintManager.CryptoObject cryptoObject;

    int count = 0;

    private SharedPreferences prefs;
    public static final String MyPREFERENCES = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.lay_lockscreen);


        prefs = getSharedPreferences(MyPREFERENCES, this.MODE_PRIVATE);

        final String email = prefs.getString("email", "");
        final String contra = prefs.getString("pass", "");


        text_email = (TextView) findViewById(R.id.text_lock_2);
        text_pass = (TextView) findViewById(R.id.text_lock_3);
        pass = (EditText) findViewById(R.id.pass_lock);
        bt_acceder = (Button) findViewById(R.id.bt_entrar);
        bt_cacelar = (Button) findViewById(R.id.bt_lock);
        huella = (ImageView) findViewById(R.id.imageView_lock);

        text_email.setText(email);

        pass.setVisibility(View.INVISIBLE);
        bt_acceder.setVisibility(View.INVISIBLE);
        text_pass.setVisibility(View.INVISIBLE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M){

            keyguardManager =
                    (KeyguardManager) getSystemService(KEYGUARD_SERVICE);

            fingerprintManager =
                    (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);

            if (!keyguardManager.isKeyguardSecure()) {

                AlertDialog.Builder builder = new AlertDialog.Builder(LockScreen.this);

                builder.setMessage("Huella Dactilar está desactivada en ajustes, activela para usar esta funcionalidad," +
                        "o pulse el botón Cancelar")
                        .setTitle("Cuidado")
                        .setPositiveButton("Vale", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                startActivity(new Intent(android.provider.Settings.ACTION_SECURITY_SETTINGS));

                                dialog.cancel();
                            }
                        });

                builder.setCancelable(false);
                AlertDialog dialog = builder.create();
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();

            }

            if (ActivityCompat.checkSelfPermission(LockScreen.this,
                    android.Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(LockScreen.this,
                        new String[]{android.Manifest.permission.USE_FINGERPRINT}, 1);
            }


            if (!fingerprintManager.hasEnrolledFingerprints()) {

                // This happens when no fingerprints are registered.

                AlertDialog.Builder builder = new AlertDialog.Builder(LockScreen.this);

                builder.setMessage("Crea al menos una 'Huella Dactilar', (Ajustes-> Seguridad -> " +
                        "Huella Dactilar), para utilizar esta funcionalidad o pulsa el botón Cancelar")
                        .setTitle("Cuidado")
                        .setPositiveButton("Vale", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                startActivity(new Intent(android.provider.Settings.ACTION_SECURITY_SETTINGS));

                                dialog.cancel();
                            }
                        });

                builder.setCancelable(false);
                AlertDialog dialog = builder.create();
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();


            }else{

                generateKey();

                if (cipherInit()) {
                    cryptoObject = new FingerprintManager.CryptoObject(cipher);
                    FingerprintHandler helper = new FingerprintHandler(this);
                    helper.startAuth(fingerprintManager, cryptoObject);
                }

            }


        } else {

            bt_cacelar.setVisibility(View.INVISIBLE);

            //  Toast.makeText(this,""+contra,Toast.LENGTH_LONG).show();

            huella.setVisibility(View.INVISIBLE);
            pass.setVisibility(View.VISIBLE);
            text_pass.setVisibility(View.VISIBLE);
            bt_acceder.setVisibility(View.VISIBLE);

            // do something for phones running an SDK before lollipop
        }



        bt_cacelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bt_cacelar.setVisibility(View.INVISIBLE);


                huella.setVisibility(View.INVISIBLE);
                pass.setVisibility(View.VISIBLE);
                text_pass.setVisibility(View.VISIBLE);
                bt_acceder.setVisibility(View.VISIBLE);
            }
        });

        bt_acceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (contra.equals(pass.getText().toString())) {

                    Intent i = new Intent(LockScreen.this, HomeScreen.class);
                    overridePendingTransition(R.anim.zoom_foward_in, R.anim.zoom_foward_out);
                    startActivity(i);
                    finish();

                } else {

                    pass.setText("");

                    AlertDialog.Builder builder = new AlertDialog.Builder(LockScreen.this);

                    builder.setMessage("Contraseña incorrecta.")
                            .setTitle("Cuidado")
                            .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    builder.setCancelable(false);
                    AlertDialog dialog = builder.create();
                    dialog.setCancelable(false);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                }
            }
        });

        pass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                pass.setBackground(getResources().getDrawable(R.drawable.back_edit_selected));
                text_pass.setVisibility(View.INVISIBLE);
            }
        });

        pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_pass.setVisibility(View.INVISIBLE);

            }
        });

        pass.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                text_pass.setVisibility(View.INVISIBLE);
                return false;
            }
        });

    }


    protected void generateKey() {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            keyGenerator = KeyGenerator.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES,
                    "AndroidKeyStore");
        } catch (NoSuchAlgorithmException |
                NoSuchProviderException e) {
            throw new RuntimeException(
                    "Failed to get KeyGenerator instance", e);
        }

        try {
            keyStore.load(null);
            keyGenerator.init(new
                    KeyGenParameterSpec.Builder(KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(
                            KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());
            keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException |
                InvalidAlgorithmParameterException
                | CertificateException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean cipherInit() {
        try {
            cipher = Cipher.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES + "/"
                            + KeyProperties.BLOCK_MODE_CBC + "/"
                            + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException |
                NoSuchPaddingException e) {
            throw new RuntimeException("Failed to get Cipher", e);
        }

        try {
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME,
                    null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
        } catch (KeyPermanentlyInvalidatedException e) {
            return false;
        } catch (KeyStoreException | CertificateException
                | UnrecoverableKeyException | IOException
                | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to init Cipher", e);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {

            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Log.i("Message ->", "Permission!");


                } else {
                    checkFingerPermission();

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;

            }
        }
    }

    private void checkFingerPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(
                this, Manifest.permission.USE_FINGERPRINT);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.USE_FINGERPRINT}, 1);
        } else {

        }
    }
}
