package co.ke.ikocare.utilities;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.EditText;
import android.widget.TextView;

public class ViewData {

    public static String getData(EditText editText){
        return editText.getText().toString();
    }

    public static String getTextViewData(TextView textView){
        return textView.getText().toString();
    }

    public static void launchMarket(Activity activity, Context context, String message) {
        Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            context.startActivity(myAppLinkToMarket);
        } catch (ActivityNotFoundException e) {
            Message.makeToast(activity,context,message);
        }
    }

}
