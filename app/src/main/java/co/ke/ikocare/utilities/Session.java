package co.ke.ikocare.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.fragment.app.FragmentManager;

import co.ke.ikocare.activities.MainActivity;

public class Session {

    public static FragmentManager fragmentManager;

    public static String helpemail = " info@ikocare.co.ke";

    private static SharedPreferences sharedpreferences;
    private static final String MyPREFERENCES = "MyPrefs";

    private static final String token = "tokenKey";
    private static final String userid = "useridKey";
    public static final String first_name = "first_nameKey";
    private static final String last_name = "last_nameKey";
    private static final String phone_number = "phone_numberKey";
    public static final String email = "emailKey";
    private static final String status = "statusKey";

    public static void sessionStoreData(String Token, String First_name, String Last_name,
                                        String Phone_number, String Email, String Status, Context context, Activity activity){

        Session.sharedpreferences = context.getSharedPreferences(MyPREFERENCES,
                Context.MODE_PRIVATE);

        try{
            sessionClearData(context);
        }catch (Exception e){
            e.printStackTrace();
        }

        SharedPreferences.Editor editor = Session.sharedpreferences.edit();
        editor.putString(token, Token);
        editor.putString(first_name, First_name);
        editor.putString(last_name, Last_name);
        editor.putString(phone_number, Phone_number);
        editor.putString(email, Email);
        editor.putString(status, Status);
        editor.apply();
        initToken(context);

        context.startActivity(new Intent(context, MainActivity.class));
        activity.finish();

    }

    public static void sessionUpdateData(String Token, String Userid, String First_name, String Last_name,
                                        String Phone_number, String Email, String Status, Context context, Activity activity){

        Session.sharedpreferences = context.getSharedPreferences(MyPREFERENCES,
                Context.MODE_PRIVATE);

        try{
            sessionClearData(context);
        }catch (Exception e){
            e.printStackTrace();
        }

        SharedPreferences.Editor editor = Session.sharedpreferences.edit();
        editor.putString(token, Token);
        editor.putString(userid, Userid);
        editor.putString(first_name, First_name);
        editor.putString(last_name, Last_name);
        editor.putString(phone_number, Phone_number);
        editor.putString(email, Email);
        editor.putString(status, Status);
        editor.apply();
        initToken(context);

        context.startActivity(new Intent(context, MainActivity.class));
        activity.finish();

    }

    public static void sessionUpdateStatus(String Status, Context context, Activity activity){

        Session.sharedpreferences = context.getSharedPreferences(MyPREFERENCES,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = Session.sharedpreferences.edit();
        editor.putString(status, Status);
        editor.apply();

        context.startActivity(new Intent(context,MainActivity.class));

        activity.finish();

    }

    static void sessionClearData(Context context){
        Session.sharedpreferences = context.getSharedPreferences(MyPREFERENCES,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = Session.sharedpreferences.edit();
        editor.clear();
        editor.apply();
    }

    public static void initToken(Context context){

        try{

            StaticVariables.TOKEN = Session.sessionGetToken(context);
            StaticVariables.phone_number = Session.sessionGetPhone(context);
            StaticVariables.first_name = Session.sessionGetFirstName(context);
            StaticVariables.userid = Session.sessionGetUserID(context);
            StaticVariables.userstatus = Session.sessionGetUserStatus(context);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private static String sessionGetToken(Context context){
        Session.sharedpreferences = context.getSharedPreferences(MyPREFERENCES,
                Context.MODE_PRIVATE);
        return Session.sharedpreferences.getString(token,null);
    }

    public static String sessionGetPhone(Context context){
        Session.sharedpreferences = context.getSharedPreferences(MyPREFERENCES,
                Context.MODE_PRIVATE);
        return Session.sharedpreferences.getString(phone_number,null);
    }

    public static String sessionGetFirstName(Context context){
        Session.sharedpreferences = context.getSharedPreferences(MyPREFERENCES,
                Context.MODE_PRIVATE);
        return Session.sharedpreferences.getString(first_name,null);
    }

    public static String sessionGetUserID(Context context){
        Session.sharedpreferences = context.getSharedPreferences(MyPREFERENCES,
                Context.MODE_PRIVATE);
        return Session.sharedpreferences.getString(userid,null);
    }

    private static String sessionGetUserStatus(Context context){
        Session.sharedpreferences = context.getSharedPreferences(MyPREFERENCES,
                Context.MODE_PRIVATE);
        return Session.sharedpreferences.getString(status,null);
    }

}
