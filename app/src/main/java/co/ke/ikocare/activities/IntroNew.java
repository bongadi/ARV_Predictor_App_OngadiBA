package co.ke.ikocare.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import co.ke.ikocare.R;
import co.ke.ikocare.utilities.PreferenceManager;

public class IntroNew extends AppCompatActivity {
    Button proceed;
    public PreferenceManager prefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_new);
        proceed = findViewById(R.id.bt_proceed);
        prefManager = new PreferenceManager(this);
        if (!prefManager.isFirstTimeLaunch()) {
            launchHomeScreen();
            finish();
        }
        
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchHomeScreen();
            }
        });
    }

    public void launchHomeScreen() {
        prefManager.setFirstTimeLaunch(false);
        startActivity(new Intent(IntroNew.this, MainActivity.class));
        finish();
    }
}