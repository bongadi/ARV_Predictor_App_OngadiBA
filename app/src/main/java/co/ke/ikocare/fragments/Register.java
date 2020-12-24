package co.ke.ikocare.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import co.ke.ikocare.R;
import co.ke.ikocare.models.auth.UserData;
import co.ke.ikocare.utilities.ApiClient;
import co.ke.ikocare.utilities.ApiInterface;
import co.ke.ikocare.utilities.ConnectionHelper;
import co.ke.ikocare.utilities.Message;
import co.ke.ikocare.utilities.Session;
import co.ke.ikocare.utilities.StaticVariables;
import co.ke.ikocare.utilities.Verification;
import co.ke.ikocare.utilities.ViewData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class Register extends Fragment {

    Login.OnLoginFormActivityListener onLoginFormActivityListener;
    EditText fName, lName, pass, passCon, email, phone;
    Button register, logIn;
    private ApiInterface apiInterface;
    ProgressBar regProgress;
    private FragmentActivity activity;
    ScrollView loginScroll;

    public Register() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        loginScroll = view.findViewById(R.id.regScroll);
        activity = getActivity();
        fName = view.findViewById(R.id.first_name);
        lName = view.findViewById(R.id.last_name);
        pass = view.findViewById(R.id.reg_password);
        passCon = view.findViewById(R.id.con_password);
        email = view.findViewById(R.id.reg_email);
        phone = view.findViewById(R.id.phone);
        register = view.findViewById(R.id.btnRegister);
        logIn = view.findViewById(R.id.btnLogin);
        regProgress = view.findViewById(R.id.register_progress);
        register.setStateListAnimator(null);
        logIn.setStateListAnimator(null);
        loginScroll.setVerticalScrollBarEnabled(false);
        loginScroll.setHorizontalScrollBarEnabled(false);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!valid()) {
                    return;
                }
                beforeReg();
            }
        });
        logIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onLoginFormActivityListener.doLoginPage();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity activity = (Activity) context;

        onLoginFormActivityListener = (Login.OnLoginFormActivityListener) activity;

    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // Refresh your fragment here
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
            Log.i("IsRefresh", "Yes");
        }
    }

    public void beforeReg() {
        StaticVariables.first_name = ViewData.getData(fName);
        StaticVariables.last_name = ViewData.getData(lName);
        StaticVariables.email = ViewData.getData(email);
        StaticVariables.phone_number = ViewData.getData(phone);
        StaticVariables.pin = ViewData.getData(pass);

        if (StaticVariables.phone_number != null){
//            Verification.SendVerification(activity,activity,StaticVariables.phone_number,regProgress,getString(R.string.register),onLoginFormActivityListener);
            SmsRetrieverClient client = SmsRetriever.getClient(activity  /* context */ );
            Task<Void> task = client.startSmsRetriever();

            task.addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Verification.SendVerification(activity,activity,StaticVariables.phone_number,regProgress,getString(R.string.register),onLoginFormActivityListener);
                    StaticVariables.log("addOnSuccessListener::onSuccess");
                }
            });

            task.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("Retriever :: ",e.getMessage());
                    StaticVariables.log("addOnFailureListener::onFailure");
                }
            });
        }else {
            Message.makeToast(activity,activity,getString(R.string.error_phone));
        }
    }

    private boolean valid() {
        boolean valid = true;
        String mail = email.getText().toString();
        String password = pass.getText().toString();
        String passwordCon = passCon.getText().toString();
        String f_name = fName.getText().toString();
        String l_name = lName.getText().toString();
        String phone_no = phone.getText().toString();

        if (f_name.isEmpty()) {
            fName.setError("First Name field required");
            valid = false;
        }else if (l_name.isEmpty()) {
            lName.setError("Last Name field required");
            valid = false;
        }else if (mail.isEmpty()) {
            email.setError("Email required");
            valid = false;
        } else  if (phone_no.isEmpty()) {
            phone.setError("Phone Number field required");
            valid = false;
        } else if (password.isEmpty()) {
            pass.setError("Password field required");
            valid = false;
        } else if (passwordCon.isEmpty()) {
            passCon.setError("Password Confirmation field required");
            valid = false;
        } else if (!passwordCon.equals(password)) {
            passCon.setError("Passwords do not match");
            valid = false;
        }else if (password.length() < 5) {
            pass.setError("password too short");
            valid = false;
        } else {
            fName.setError(null);
            lName.setError(null);
            email.setError(null);
            phone.setError(null);
            pass.setError(null);
            passCon.setError(null);
        }

        return valid;
    }
}
