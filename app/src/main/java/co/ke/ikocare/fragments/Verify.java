package co.ke.ikocare.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.android.gms.auth.api.phone.SmsRetriever;
//import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.gms.tasks.Task;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.Objects;

import co.ke.ikocare.App;
import co.ke.ikocare.R;
import co.ke.ikocare.models.auth.UserData;
import co.ke.ikocare.models.auth.VerifyCode;
import co.ke.ikocare.utilities.ApiClient;
import co.ke.ikocare.utilities.ApiInterface;
import co.ke.ikocare.utilities.ConnectionHelper;
import co.ke.ikocare.utilities.Message;
import co.ke.ikocare.utilities.Session;
import co.ke.ikocare.utilities.StaticVariables;
import co.ke.ikocare.utilities.Validator;
import co.ke.ikocare.utilities.Verification;
import co.ke.ikocare.utilities.ViewData;
import io.objectbox.Box;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class Verify extends Fragment {

    Login.OnLoginFormActivityListener onLoginFormActivityListener;
    TextView title,txtmessage,or;
    Context context;
    Activity activity;
    EditText etverifycode;
    Button btnverify,btnresend,btProceed;
    ProgressBar progressWheel;
    String calleractivity;
    ProgressBar progressbar;
    int i = 0;
    CountDownTimer mCountDownTimer;
    private Box<UserData> userDataBox;

    private ApiInterface apiInterface;

    public Verify() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_verify, container, false);
        activity = getActivity();
        onLoginFormActivityListener = (Login.OnLoginFormActivityListener) activity;
        userDataBox = ((App)getActivity().getApplicationContext()).getBoxStore().boxFor(UserData.class);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        calleractivity = StaticVariables.CALLER;

        progressbar = view.findViewById(R.id.code_progress);
        txtmessage = view.findViewById(R.id.ver_info_tv);
        etverifycode = view.findViewById(R.id.et_ver_code);
        btnverify = view.findViewById(R.id.btn_verify);
        btnresend = view.findViewById(R.id.bt_resend_code);
        progressWheel = view.findViewById(R.id.prog_code);
        btProceed = view.findViewById(R.id.bt_login_proceed);
        or = view.findViewById(R.id.or_tv);

        btProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoginFormActivityListener.doLoginPage();
            }
        });

        btnverify.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {

                try {
                    InputMethodManager imm = (InputMethodManager)activity.getSystemService(INPUT_METHOD_SERVICE);
                    Objects.requireNonNull(imm).hideSoftInputFromWindow(Objects.requireNonNull(activity.getCurrentFocus()).getWindowToken(),0);
                } catch(Exception e) {
                    e.printStackTrace();
                }

                if(!Validator.isOfFixedLength(etverifycode,6)){
                    etverifycode.requestFocus();
                    etverifycode.setError(getString(R.string.code_length_error));
                }else {
//                    Toast.makeText(activity,"Hit Verify Top", Toast.LENGTH_LONG).show();

                    if(StaticVariables.phone_number != null){
//                        Toast.makeText(activity,"Hit Verify", Toast.LENGTH_LONG).show();
                        verifyCode(StaticVariables.phone_number, ViewData.getData(etverifycode));
                        btProceed.setVisibility(View.VISIBLE);
                        btnresend.setVisibility(View.GONE);
                        btnverify.setVisibility(View.GONE);
                        or.setVisibility(View.GONE);

                    }

//                    if(calleractivity != null){
//
//                        if(calleractivity.equals(getString(R.string.register))){
//                            if(StaticVariables.phone_number != null){
//                                Toast.makeText(activity,"Hit Verify", Toast.LENGTH_LONG).show();
//                                verifyCode(StaticVariables.phone_number, ViewData.getData(etverifycode));
//
//                            }
//                        }
//
//                    }

                }

            }
        });

        btnresend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (calleractivity != null){
//                    if(calleractivity.equals(getString(R.string.register))){

                        if(StaticVariables.phone_number != null){

//                            Verification.ReSendVerification(activity,activity,StaticVariables.phone_number,progressWheel);

                            // Get an instance of SmsRetrieverClient, used to start listening for a matching
                            // SMS message.
                            SmsRetrieverClient client = SmsRetriever.getClient(context  /* context */ );

                            // Starts SmsRetriever, which waits for ONE matching SMS message until timeout
                            // (5 minutes). The matching SMS message will be sent via a Broadcast Intent with
                            // action SmsRetriever#SMS_RETRIEVED_ACTION.
                            Task<Void> task = client.startSmsRetriever();

                            // Listen for success/failure of the start Task. If in a background thread, this
                            // can be made blocking using Tasks.await(task, [timeout]);
                            task.addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(activity," Hit Resend", Toast.LENGTH_LONG).show();

                                    Verification.ReSendVerification(activity,activity,StaticVariables.phone_number,progressWheel);

                                    // Successfully started retriever, expect broadcast intent
                                    // ...

                                    StaticVariables.log("addOnSuccessListener::onSuccess");
                                }
                            });

                            task.addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Failed to start retriever, inspect Exception for more details
                                    // ...
                                    Log.d("Retriever :: ",e.getMessage());
                                    StaticVariables.log("addOnFailureListener::onFailure");
                                }
                            });

                            //Verification.ReSendVerification(activity,context,StaticVariables.phone_number,progressWheel);

                        }

//                    }
//                }
            }
        });

        progressbar.setProgress(i);
        mCountDownTimer=new CountDownTimer(300000,1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                //Log.v("Log_tag", "Tick of Progress"+ i + millisUntilFinished);
                i++;
                progressbar.setProgress(i * 100/(300000/1000));

            }

            @Override
            public void onFinish() {
                //Do what you want
                i++;
                progressbar.setProgress(100);
                //btnresend.callOnClick();
                i = 0;
                progressbar.setProgress(i);
                mCountDownTimer.start();
            }
        };
        mCountDownTimer.start();

        return view;
    }

    public void verifyCode(String phone, String code){
        mCountDownTimer.cancel();

        progressWheel.setVisibility(View.VISIBLE);
        Call<VerifyCode> call = apiInterface.verifycode(phone,code);

        call.enqueue(new Callback<VerifyCode>() {
            @Override
            public void onResponse(Call<VerifyCode> call, Response<VerifyCode> response) {
//                Message.makeToast(activity,activity,"Top of verify");

                VerifyCode verifyCode = response.body();

                try{

                    if(verifyCode != null){

//                        if(calleractivity != null){

//                            if(calleractivity.equals(getString(R.string.register))){
                        Message.makeToast(activity,activity,verifyCode.getMessage());

                                if(verifyCode.getMessage().equals("code verified successfully")){
                                    saveUser();
                                    Message.makeToast(activity,activity,verifyCode.getMessage());
//                                    onLoginFormActivityListener.doLoginPage();
                                }

//                            }

//                        }

                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
                progressWheel.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<VerifyCode> call, Throwable t) {

                progressWheel.setVisibility(View.GONE);
                ConnectionHelper.launchConnectionAlert(activity,context,t.getMessage());

            }
        });
    }

    public void saveUser() {

        try {

            Call<co.ke.ikocare.models.auth.Register> call = apiInterface.register(StaticVariables.first_name,
                    StaticVariables.last_name, StaticVariables.email, StaticVariables.phone_number,
                    StaticVariables.pin);

            call.enqueue(new Callback<co.ke.ikocare.models.auth.Register>() {
                @Override
                public void onResponse(Call<co.ke.ikocare.models.auth.Register> call, Response<co.ke.ikocare.models.auth.Register> response) {
                    co.ke.ikocare.models.auth.Register respo = response.body();
                    try {
                        if (respo != null){
                            UserData userData = respo.getData();
                            if(respo.getToken().equals("NONE")){

                                Message.makeToast(activity,activity,respo.getMessage());

                            }else {

                                Message.makeToast(activity,activity,respo.getMessage());

                                if(userData != null){
                                    userDataBox.removeAll();
                                    userDataBox.put(userData);
                                    Session.sessionStoreData(respo.getToken(), userData.getFirstName(), userData.getSecondName(),
                                            userData.getPhoneNumber(),userData.getEmail(), userData.getStatus().toString(), activity,activity);
                                    StaticVariables.email = userData.getEmail();

                                    Message.makeToast(activity,activity,"Registration Successful");

                                }

                            }
                        }else {
                            Message.makeToast(activity,activity,"response is null!");
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(Call<co.ke.ikocare.models.auth.Register> call, Throwable t) {
                    ConnectionHelper.launchConnectionAlert(activity,activity,t.getMessage());
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
