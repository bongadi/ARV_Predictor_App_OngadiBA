package co.ke.ikocare.utilities;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import co.ke.ikocare.R;

public class Message {

    public static void makeToast(Activity activity, Context context, String message){

        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM,0,140);
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View appear = layoutInflater.inflate(R.layout.toast_layout,(ViewGroup)activity.findViewById(R.id.toast_linear));
        TextView txtmessage = appear.findViewById(R.id.txtmessage);
        txtmessage.setText(message);
        toast.setView(appear);
        toast.show();

    }

    public static void makeToastFragment(FragmentActivity activity, Context context, String message){

        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM,0,140);
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View appear = layoutInflater.inflate(R.layout.toast_layout,(ViewGroup)activity.findViewById(R.id.toast_linear));
        TextView txtmessage = appear.findViewById(R.id.txtmessage);
        txtmessage.setText(message);
        toast.setView(appear);
        toast.show();

    }

}
