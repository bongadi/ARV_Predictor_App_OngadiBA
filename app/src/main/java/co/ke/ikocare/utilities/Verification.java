package co.ke.ikocare.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import co.ke.ikocare.R;
import co.ke.ikocare.fragments.Login;
import co.ke.ikocare.fragments.Verify;
import retrofit2.Call;
import retrofit2.Callback;

public class Verification {

    private static ApiInterface apiInterface;

    public static void SendVerification(final FragmentActivity activity, final Context context, final String phone, final ProgressBar progressWheel, final String caller, final Login.OnLoginFormActivityListener onLoginFormActivityListener) {

        progressWheel.setVisibility(View.VISIBLE);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<SendCode> call = apiInterface.sendcode(phone);

        call.enqueue(new Callback<SendCode>() {
            @Override
            public void onResponse(@NonNull Call<SendCode> call, @NonNull retrofit2.Response<SendCode> response) {

                SendCode sendCode = response.body();

                try{

                    if (sendCode != null) {

                        Message.makeToast(activity, context, sendCode.getMessage());

                        if(!sendCode.getData().equals("false")){
                            onLoginFormActivityListener.doCode();

                        }

                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

                progressWheel.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(@NonNull Call<SendCode> call, @NonNull Throwable t) {
                progressWheel.setVisibility(View.GONE);
                StaticVariables.phone_number = phone;
                ConnectionHelper.launchConnectionAlert(activity,context,t.getMessage());
            }
        });

    }

    public static void ReSendVerification(final Activity activity, final Context context, final String phone, final ProgressBar progressWheel) {

        progressWheel.setVisibility(View.VISIBLE);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<SendCode> call = apiInterface.sendcode(phone);

        call.enqueue(new Callback<SendCode>() {
            @Override
            public void onResponse(@NonNull Call<SendCode> call, @NonNull retrofit2.Response<SendCode> response) {

                SendCode sendCode = response.body();

                try{

                    if (sendCode != null) {

                        Message.makeToast(activity, context, sendCode.getMessage());

                    }


                }catch (Exception e){
                    e.printStackTrace();
                }

                progressWheel.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(@NonNull Call<SendCode> call, @NonNull Throwable t) {
                progressWheel.setVisibility(View.GONE);
                StaticVariables.phone_number = phone;
                ConnectionHelper.launchConnectionAlert(activity,context,t.getMessage());
            }
        });

    }

}
