package com.example.fingerprint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {

    TextView tvMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvMsg = findViewById(R.id.tvMessage);

        BiometricManager biometricManager = BiometricManager.from(this);

        switch (biometricManager.canAuthenticate())
        {
            case BiometricManager.BIOMETRIC_SUCCESS:
                tvMsg.setText("Use Fingerprint Sensor to Login....");
            break;

            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                tvMsg.setText("Device Do Not have Fingerprint Sensor....");
                break;

            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                tvMsg.setText("Biometric Sensor is currently Unavailable");
                break;

                case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                    tvMsg.setText("Device have do not have any Fingerprint Saved,please check your setting....");
                    break;
        }

        Executor executor = ContextCompat.getMainExecutor(this);
        BiometricPrompt biometricPrompt = new BiometricPrompt(this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(MainActivity.this,"Error", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(MainActivity.this,"Login Success", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(MainActivity.this,"Login Failed", Toast.LENGTH_LONG).show();

            }
        });

        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Login")
                .setSubtitle("Use Finger to Login")
                .setNegativeButtonText("Cancle")
                .build();

        biometricPrompt.authenticate(promptInfo);

    }
}