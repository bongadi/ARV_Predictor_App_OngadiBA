package co.ke.ikocare.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import co.ke.ikocare.App;
import co.ke.ikocare.R;
import co.ke.ikocare.models.auth.Register;
import co.ke.ikocare.models.auth.UserData;
import co.ke.ikocare.utilities.ApiClient;
import co.ke.ikocare.utilities.ApiInterface;
import co.ke.ikocare.utilities.ConnectionHelper;
import co.ke.ikocare.utilities.Message;
import co.ke.ikocare.utilities.PreferenceManager;
import co.ke.ikocare.utilities.Session;
import co.ke.ikocare.utilities.StaticVariables;
import co.ke.ikocare.utilities.UiEffects;
import co.ke.ikocare.utilities.ViewData;
import io.objectbox.Box;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class Login extends Fragment {

    private static EditText email, password;
    private static Button reg,login;
    private ProgressBar loginProgress;
    private FragmentActivity activity;
    private TextView forgottPass,signTv;
    private OnLoginFormActivityListener onLoginFormActivityListener;
    private ApiInterface apiInterface;
    private static PreferenceManager preferenceManager;
    private Box<UserData> userDataBox;
    public Login() {
        // Required empty public constructor
    }

    public interface OnLoginFormActivityListener {
        public void passResetCancel();
        public void doLogin();
        public void doLoginPage();
        public void doRegister();
        public void doCode();
        public void doDrugAnalysis();
        public void doProducts();
        public void doServices();
        public void doFaqs();
        public void doInvite();
        public void doProfile();
        public void doMyServices();
        public void doLoader();
        public void doPassReset();
        public void finish();
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        email = view.findViewById(R.id.tv_log_phone);
        password = view.findViewById(R.id.tv_log_password);
        reg = view.findViewById(R.id.btl_log_reg);
        login = view.findViewById(R.id.btn_log_sign_in);
        loginProgress = view.findViewById(R.id.login_progress);
        loginProgress.setVisibility(View.GONE);
        forgottPass = view.findViewById(R.id.pass_forgot);
        signTv = view.findViewById(R.id.sign_tv);
        activity = getActivity();
        reg.setStateListAnimator(null);
        login.setStateListAnimator(null);
        preferenceManager = new PreferenceManager(getActivity());
        userDataBox = ((App)getActivity().getApplicationContext()).getBoxStore().boxFor(UserData.class);

//        try {
//            email.setText(Session.sessionGetPhone(getContext()));
//
//            if(!email.getText().toString().equals("")){
//                password.requestFocus();
//                Message.makeToast(activity,getContext(),"enter pin to continue");
//            }
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!valid()) {
                    loginProgress.setVisibility(View.GONE);
                    return;
                }
                login(ViewData.getData(email), ViewData.getData(password));
                preferenceManager.setLoginStatus(true);
            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoginFormActivityListener.doRegister();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity activity = (Activity) context;

        onLoginFormActivityListener = (OnLoginFormActivityListener) activity;

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

    public void login(String phone, String pin) {

        loginProgress.setVisibility(View.VISIBLE);
        email.setVisibility(View.GONE);
        password.setVisibility(View.GONE);
        reg.setVisibility(View.GONE);
        login.setVisibility(View.GONE);
        forgottPass.setText("Signing In ...");
        forgottPass.setTextSize(25);
        signTv.setVisibility(View.GONE);


        try {

            Call<Register> call = apiInterface.login(phone,pin);

            call.enqueue(new Callback<Register>() {
                @Override
                public void onResponse(Call<Register> call, Response<Register> response) {
                    Register respo = response.body();
//                    Toast.makeText(activity,respo.toString(),Toast.LENGTH_LONG).show();
                    if (respo != null) {

                        UserData userData = respo.getData();

                        if(respo.getToken().equals("NONE")){

                            Message.makeToast(activity,activity,respo.getMessage());
//                            Toast.makeText(activity,respo.getMessage(),Toast.LENGTH_LONG).show();

                        }else {
                            if (userData != null) {
                                userDataBox.removeAll();
                                userDataBox.put(userData);
                                Log.i("TOKEN", respo.getToken());
                                Log.i("FIRST", userData.getFirstName());
                                Log.i("LAST", userData.getSecondName());
                                Log.i("PHONE", userData.getPhoneNumber());
                                Log.i("EMAIL", userData.getEmail());
                                Log.i("STATUS", userData.getStatus().toString());
                                Session.sessionStoreData(respo.getToken(), userData.getFirstName(), userData.getSecondName(),
                                        userData.getPhoneNumber(),userData.getEmail(), userData.getStatus().toString(), activity,activity);
                                StaticVariables.first_name = userData.getFirstName();
                                StaticVariables.email = userData.getEmail();

                            }
                            onLoginFormActivityListener.doLogin();
                            Message.makeToast(activity,activity,respo.getMessage());
                            PreferenceManager preferenceManager = new PreferenceManager(activity);
                            preferenceManager.setLoginStatus(true);
//                            Toast.makeText(activity,respo.getMessage(),Toast.LENGTH_LONG).show();

//                            Log.d("TOKEN")

                        }

                    } else {
//                        Toast.makeText(activity,"Response is null",Toast.LENGTH_LONG).show();
                        Message.makeToast(activity,activity, "Response is null!");

                    }

                    loginProgress.setVisibility(View.GONE);
                    email.setVisibility(View.VISIBLE);
                    password.setVisibility(View.VISIBLE);
                    reg.setVisibility(View.VISIBLE);
                    login.setVisibility(View.VISIBLE);
                    forgottPass.setText("Forgot Password?");
                    forgottPass.setTextSize(15);
                    signTv.setVisibility(View.VISIBLE);
                }

                @Override
                public void onFailure(Call<Register> call, Throwable t) {
                    loginProgress.setVisibility(View.GONE);
                    email.setVisibility(View.VISIBLE);
                    password.setVisibility(View.VISIBLE);
                    reg.setVisibility(View.VISIBLE);
                    login.setVisibility(View.VISIBLE);
                    forgottPass.setText("Forgot Password?");
                    forgottPass.setTextSize(15);
                    signTv.setVisibility(View.VISIBLE);
                    Message.makeToast(activity,activity, t.getMessage());
                    ConnectionHelper.launchConnectionAlert(activity,activity,t.getMessage());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private boolean valid() {
        boolean valid = true;
        String mail = email.getText().toString();
        String pass = password.getText().toString();

        if (mail.isEmpty()) {
            email.setError("Phone Number is required");
            valid = false;
        } else if (pass.isEmpty()) {
            password.setError("Password field required");
            valid = false;
        } else if (pass.length() < 5) {
            password.setError("password too short");
            valid = false;
        } else {
            email.setError(null);
            password.setError(null);
        }

        return valid;
    }
}
