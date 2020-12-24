package co.ke.ikocare.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import co.ke.ikocare.R;
import co.ke.ikocare.utilities.Message;
import co.ke.ikocare.utilities.StaticVariables;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class ConnectionAlert extends AppCompatActivity {

    Context context;
    Activity activity;
    Button btnRetry;
    ProgressBar progressWheel;
    String calleractivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection_alert);
        context = this;
        activity = this;
        Intent intent = getIntent();
        calleractivity = intent.getStringExtra(StaticVariables.CALLER);

        btnRetry = findViewById(R.id.bt_retry);
        progressWheel = findViewById(R.id.retry_progress);

        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(calleractivity != null){

                    String activityToStart = calleractivity;

                    try {

                        Class<?> classtostart = Class.forName(activityToStart);
                        Intent intent = new Intent(context, classtostart);
                        startActivity(intent);

                    }
                    catch (ClassNotFoundException ignored) {

                        ignored.printStackTrace();

                    }

                    finish();

                }else {
                    Message.makeToast(activity,context,getString(R.string.txterrorcalleractivity));
                }

            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }
}
