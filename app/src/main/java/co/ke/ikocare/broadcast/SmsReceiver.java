package co.ke.ikocare.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;

import androidx.annotation.RequiresApi;

import java.util.Objects;

public class SmsReceiver extends BroadcastReceiver {

    private static SmsListener mListener;
    Boolean b;
    String code;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle data  = intent.getExtras();
        Object[] pdus = (Object[]) Objects.requireNonNull(data).get("pdus");

        for(int i = 0; i< Objects.requireNonNull(pdus).length; i++){

            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);
            String sender = smsMessage.getDisplayOriginatingAddress();
            //b=sender.endsWith("ASTKNG");  //Just to fetch otp sent from ASTKNG
            String messageBody = smsMessage.getMessageBody();
            code = messageBody.replaceAll("[^0-9]","");   // here abcd contains otp

            //Pass on the text to our listener.
            if(sender.equals("AFRINETSMS")) {

                mListener.messageReceived(code);  // attach value to interface

            } else {

            }

        }

    }

    public static void bindListener(SmsListener listener) {
        mListener = listener;
    }
}
